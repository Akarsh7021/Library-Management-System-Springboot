package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.BorrowRenewalDate;
import ca.kpu.info2413.library.backend.repository.BorrowRenewalDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowRenewalDateService
{
    @Autowired
    BorrowRenewalDateRepository borrowRenewalDateRepository;


    public List<BorrowRenewalDate> findAll()
    {
        return borrowRenewalDateRepository.findAll();
    }

    public BorrowRenewalDate save(BorrowRenewalDate borrowRenewalDate)
    {
        return borrowRenewalDateRepository.save(borrowRenewalDate);
    }

    public void deleteByBorrowIdBorrow(Integer borrowIdBorrow)
    {
        borrowRenewalDateRepository.deleteByBorrowIdBorrow(borrowIdBorrow);
    }

    /// ////////////////////

    public List<BorrowRenewalDate> findByBorrowIdBorrow(Integer borrowIdBorrow)
    {
        return borrowRenewalDateRepository.findByBorrowIdBorrow(borrowIdBorrow);
    }

    public List<BorrowRenewalDate> findByRenewalDate(LocalDate renewalDate)
    {
        return borrowRenewalDateRepository.findByRenewalDate(renewalDate);
    }

}

