package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.*;
import ca.kpu.info2413.library.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FineService
{
    @Autowired
    FineRepository fineRepository;
    @Autowired
    private AccountRepository accountRepository;

    public List<Fine> findAll()
    {
        return fineRepository.findAll();
    }

    public Optional<Fine> findByFineId(Integer fineId)
    {
        return fineRepository.findByFineId(fineId);
    }

    public Fine save(Fine fine)
    {
        return fineRepository.save(fine);
    }

    public void deleteByFineId(Integer fineId)
    {
        fineRepository.deleteById(fineId);
    }

    /// ////////////////////


    //redo using service later

    public Optional<Fine> findByFineId(Integer fineId)
    {
        return fineRepository.findByFineId(fineId);
    }

    public List<Fine> findByBorrowIdBorrow(Integer borrowIdBorrow)
    {
        return fineRepository.findByBorrowIdBorrow(borrowIdBorow);
    }
    //To search if an account has a fine or list accounts that have fines??
    public List<Account> findByAccountId(Integer accountId)
    {
        return accountRepository.findByAccountId(accountId);
    }

}
