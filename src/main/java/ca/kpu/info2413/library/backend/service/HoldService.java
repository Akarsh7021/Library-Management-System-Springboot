package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Hold;
import ca.kpu.info2413.library.backend.repository.HoldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HoldService
{
    @Autowired
    HoldRepository holdRepository;


    public List<Hold> findAll()
    {
        return holdRepository.findAll();
    }

    public List<Hold> findByBookHoldId(Integer bookHoldId)
    {
        return holdRepository.findByBookHoldId(bookHoldId);
    }

    public Hold save(Hold hold)
    {
        return holdRepository.save(hold);
    }

    public void deleteByBookHoldId(Integer bookHoldId)
    {
        holdRepository.deleteById(bookHoldId);
    }

    /// ////////////////////
    public List<Hold> findBySerialBarcodeBookCopy(Integer serialBarcodeBookCopy)
    {
        return holdRepository.findBySerialBarcodeBookCopy(serialBarcodeBookCopy);
    }

    public List<Hold> findByAccountId(Integer accountId)
    {
        return holdRepository.findByAccountIdAccount(accountId);
    }

    public Optional<Hold> findByBookAndAccount(Integer serialBarcodeBookCopy, Integer accountIdAccount)
    {
        return holdRepository.findFirstBySerialBarcodeBookCopyAndAccountIdAccount(serialBarcodeBookCopy, accountIdAccount);
    }

    public Hold createHold(Integer bookId, Integer accountId)
    {
        Hold hold = new Hold();
        hold.setSerialBarcodeBookCopy(bookId);
        hold.setAccountIdAccount(accountId);

        // Set the date it was put on hold
        LocalDate today = LocalDate.now();
        hold.setHeldSince(today);

        // Set expiry to 2 weeks later
        hold.setHoldExpiry(today.plusWeeks(2));

        return holdRepository.save(hold);
    }

    public boolean cancelHold(Integer bookId, Integer accountId)
    {
        Optional<Hold> opt = findByBookAndAccount(bookId, accountId);
        if (opt.isPresent())
        {
            holdRepository.delete(opt.get());
            return true;
        }
        return false;
    }
}

