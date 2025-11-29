package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.AccountPublication;
import ca.kpu.info2413.library.backend.model.AccountPublicationId;
import ca.kpu.info2413.library.backend.model.Publication;
import ca.kpu.info2413.library.backend.repository.AccountPublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return saved.getWaitlistPosition();
    }

    @Transactional
    public boolean removeFromWaitlist(Long isbn, Integer accountId) {
        Optional<AccountPublication> opt = repo.findByIsbnAndAccount(isbn, accountId);
        if (opt.isEmpty()) return false;
        AccountPublication toRemove = opt.get();
        repo.delete(toRemove);

        // After deletion, to keep positions contiguous you might want to shift everybody behind up by 1.
        // (Optional) Implement re-sequencing here if desired.
        return true;
    }
}
