package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.*;
import ca.kpu.info2413.library.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Hold> findByBookHoldId(Integer bookHoldId)
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


    //redo using service later

    public Optional<Hold> findByBookHoldId(Integer bookHoldId)
    {
        return holdRepository.findByBookHoldId(bookHoldId);
    }

    public List<Hold> findBySerialBarcodeBookCopy(String serialBarcodeBookCopy)
    {
        return holdRepository.findBySerialBarcodeBookCopy(serialBarcodeBookCopy);
    }

    public List<Hold> findByAccountId(Integer accountId)
    {
        return holdRepository.findByAccountId(accountId);
    }

}
