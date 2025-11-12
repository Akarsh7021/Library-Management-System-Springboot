package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Hold;
import ca.kpu.info2413.library.backend.repository.HoldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
