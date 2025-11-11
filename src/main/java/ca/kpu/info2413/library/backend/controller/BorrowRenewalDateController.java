package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.Author;
import ca.kpu.info2413.library.backend.model.BorrowRenewalDate;
import ca.kpu.info2413.library.backend.service.AccountService;
import ca.kpu.info2413.library.backend.service.AuthorService;
import ca.kpu.info2413.library.backend.service.BorrowRenewalDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/borrow_renewal_date")
public class BorrowRenewalDateController
{

    @Autowired
    private BorrowRenewalDateService borrowRenewalDateService;

    @GetMapping
    public List<BorrowRenewalDate> findAll()
    {
        return borrowRenewalDateService.findAll();
    }

    @GetMapping("/{borrow_id_borrow}")
    public List<BorrowRenewalDate> findByBorrowIdBorrow(@PathVariable Integer borrow_id_borrow)
    {
        return BorrowRenewalDateService.findByBorrowIdBorrow(borrow_id_borrow);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BorrowRenewalDate create(@RequestBody BorrowRenewalDate borrowRenewalDate)
    {
        return borrowRenewalDateService.save(borrowRenewalDate);
    }

    @PutMapping
    public BorrowRenewalDate update(@RequestBody BorrowRenewalDate borrowRenewalDate)
    {
        return borrowRenewalDateService.save(borrowRenewalDate);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{borrow_id_borrow}")
    public void deleteByBorrowIdBorrow(@PathVariable Integer borrow_id_borrow)
    {
        BorrowRenewalDateService.deleteByBorrowIdBorrow(borrow_id_borrow);
    }

    /// ///

    @GetMapping("/find/renewal_date/{renewal_date}")
    public List<BorrowRenewalDate> findByRenewalDate(@PathVariable LocalDate renewal_date)
    {
        return borrowRenewalDateService.findByRenewalDate(renewal_date);
    }

}
