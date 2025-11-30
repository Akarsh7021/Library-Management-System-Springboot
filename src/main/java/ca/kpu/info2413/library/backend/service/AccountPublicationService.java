package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.DTO.AccountPublicationDTO;
import ca.kpu.info2413.library.backend.model.*;
import ca.kpu.info2413.library.backend.repository.AccountPublicationRepository;
import ca.kpu.info2413.library.backend.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountPublicationService
{
    @Autowired
    private AccountPublicationRepository repo;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private HoldService holdService;

    public List<AccountPublication> getWaitlistForIsbn(Long isbn) {
        return repo.findByPublicationIsbn13OrderByPosition(isbn);
    }

    public List<AccountPublication> getWaitlistForAccount(Integer accountId) {
        return repo.findByAccountAccountId(accountId);
    }

    public Optional<AccountPublication> findByIsbnAndAccount(Long isbn, Integer accountId) {
        return repo.findByIsbnAndAccount(isbn, accountId);
    }

    @Transactional
    public short addToWaitlist(Long isbn, Integer accountId) throws IllegalArgumentException {
        // lookup publication entity (use service method returning Optional<Publication>)
        var pubOpt = publicationService.findEntityByIsbn13(isbn);
        if (pubOpt.isEmpty()) {
            throw new IllegalArgumentException("Publication not found for ISBN: " + isbn);
        }
        Publication publication = pubOpt.get();

        // check account exists
        var accs = accountService.findByAccountId(accountId);
        if (accs == null || accs.isEmpty()) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        Account account = accs.getFirst();

        // do not add duplicate
        Optional<AccountPublication> existing = repo.findByIsbnAndAccount(isbn, accountId);
        if (existing.isPresent()) {
            return existing.get().getWaitlistPosition() != null ? existing.get().getWaitlistPosition() : (short) -1;
        }

        Short maxPos = repo.findMaxWaitlistPositionForIsbn(isbn);
        short nextPos = (maxPos == null) ? (short)1 : (short)(maxPos + 1);

        AccountPublication ap = AccountPublication.builder()
                .account(account)
                .publication(publication)
                .waitlistPosition(nextPos)
                .build();

        // If AccountPublication requires setting its embedded id explicitly, uncomment and set it:
        ap.setId(new AccountPublicationId(account.getAccountId(), publication.getIsbn13()));

        AccountPublication saved = repo.save(ap);
        processWaitlistForPublication(isbn);
        return saved.getWaitlistPosition();
    }

    /**
     * If any copies for the given publication ISBN are available and the waitlist is non-empty,
     * remove the next waitlist member and create a Hold for an available copy for that account.
     * Safe to call after a copy is returned, or after waitlist changes.
     */
    @Transactional
    public boolean processWaitlistForPublication(Long isbn) {
        // 1) find next waitlist entry
        Optional<AccountPublication> nextOpt = repo.findFirstByPublicationIsbn13OrderByWaitlistPositionAsc(isbn);
        if (nextOpt.isEmpty()) {
            return false; // no waitlist
        }
        AccountPublication next = nextOpt.get();

        // 2) find an available copy for the publication
        List<BookCopy> available = bookCopyRepository.findAvailableCopiesByIsbn(isbn);
        if (available == null || available.isEmpty()) {
            return false; // nothing to assign right now
        }
        BookCopy chosen = available.getFirst(); // pick first available (could use other policy)

        // 3) create a hold for the chosen copy and account
        Integer accountId = next.getAccount().getAccountId();
        Integer serialBarcode = chosen.getSerialBarcode();

        // Ensure no existing hold exists for this pair (race-guard)
        Optional<Hold> existingHold = holdService.findByBookAndAccount(serialBarcode, accountId);
        if (existingHold.isPresent()) {
            // Strange: already on hold; remove waitlist entry and continue (or just skip)
            // We'll remove the waitlist entry and shift positions (so the next person gets checked next time).
            short removedPos = next.getWaitlistPosition();
            repo.delete(next);
            return true;
        }

        // create the hold (HoldService.createHold sets heldSince/expiry and saves)
        Hold createdHold = holdService.createHold(serialBarcode, accountId);
        if (createdHold == null) {
            // couldn't create hold for some reason
            return false;
        }

        // 4) remove the waitlist entry and shift remaining positions down
        short removedPos = next.getWaitlistPosition();
        repo.delete(next);

        // success
        return true;
    }

    @Transactional(readOnly = true)
    public List<AccountPublicationDTO> getWaitlistForAccountDto(Integer accountId) {
        List<AccountPublication> list = repo.findByAccountAccountId(accountId);
        List<AccountPublicationDTO> dtos = new ArrayList<>();
        for (AccountPublication ap : list) {
            // access lazy properties while inside @Transactional
            Long isbn = ap.getPublication() != null ? ap.getPublication().getIsbn13() : null;
            String title = ap.getPublication() != null ? ap.getPublication().getTitle() : null;
            Integer accId = ap.getAccount() != null ? ap.getAccount().getAccountId() : null;
            String accName = ap.getAccount() != null ? ap.getAccount().getFullName() : null;
            Short pos = ap.getWaitlistPosition();
            dtos.add(new AccountPublicationDTO(isbn, title, accId, accName, pos));
        }
        return dtos;
    }

    @Transactional
    public boolean removeFromWaitlist(Long isbn, Integer accountId) {
        Optional<AccountPublication> opt = repo.findByIsbnAndAccount(isbn, accountId);
        if (opt.isEmpty()) return false;
        AccountPublication toRemove = opt.get();
        repo.delete(toRemove);
        processWaitlistForPublication(isbn);
        return true;
    }
}
