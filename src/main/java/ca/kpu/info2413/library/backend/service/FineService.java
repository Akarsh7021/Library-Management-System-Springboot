package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Borrow;
import ca.kpu.info2413.library.backend.model.Fine;
import ca.kpu.info2413.library.backend.repository.AccountRepository;
import ca.kpu.info2413.library.backend.repository.BorrowRepository;
import ca.kpu.info2413.library.backend.repository.FineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FineService
{
    @Autowired
    FineRepository fineRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BorrowRepository borrowRepository;

    public List<Fine> findAll()
    {
        return fineRepository.findAll();
    }

    public List<Fine> findByFineId(Integer fineId)
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

    public List<Fine> findByBorrowIdBorrow(Integer borrowIdBorrow)
    {
        return fineRepository.findByBorrowIdBorrow(borrowIdBorrow);
    }
    //To search if an account has a fine or list accounts that have fines??
    public List<Fine> findByAccountId(Integer accountId)
    {
        ArrayList<Fine> fineList = new ArrayList<>();

        for (Borrow b : borrowRepository.findByAccountIdAccount(accountId))
        {
            fineList.addAll(fineRepository.findByBorrowIdBorrow(b.getBorrowId()));
        }

        return fineList;
    }

}
