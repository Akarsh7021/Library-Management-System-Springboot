package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Configuration;
import ca.kpu.info2413.library.backend.model.Hold;
import ca.kpu.info2413.library.backend.repository.ConfigurationRepository;
import ca.kpu.info2413.library.backend.repository.HoldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HoldService
{
    @Autowired
    HoldRepository holdRepository;
    @Autowired
    ConfigurationRepository configurationRepository;


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

        // Set expiry according to settings
        Optional<Configuration> holdConfig = configurationRepository.findById("holdLength");

        if(holdConfig.isPresent()) {
            hold.setHoldExpiry(today.plusDays(Long.parseLong(holdConfig.get().getConfigValue())));
        } else { // default 2 weeks
            hold.setHoldExpiry(today.plusWeeks(2));
        }

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

    // check and cancel all expired holds
    @Scheduled(cron = "0 0 * * * *")
    public void updateExpiredHolds()
    {
        LocalDate today = LocalDate.now();

        // find all holds with expiry date before today
        List<Hold> expiredHolds = holdRepository.findByHoldExpiryBefore(today);

        if(expiredHolds.isEmpty()) return;

        holdRepository.deleteAll(expiredHolds);
    }
}

