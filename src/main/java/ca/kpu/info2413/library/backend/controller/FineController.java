package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.Fine;
import ca.kpu.info2413.library.backend.service.AccountService;
import ca.kpu.info2413.library.backend.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class FineController
{

    @Autowired
    private FineService fineService;

    @GetMapping
    public List<Fine> findAll()
    {
        return fineService.findAll();
    }

    @GetMapping("/{fine_id}")
    public List<Fine> findByFineId(@PathVariable Integer fine_id)
    {
        return fineService.findByFineId(fine_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Fine create(@RequestBody Fine fine)
    {
        return fineService.save(fine);
    }

    @PutMapping
    public Fine update(@RequestBody Fine fine)
    {
        return fineService.save(fine);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{fine_id}")
    public void deleteByFineId(@PathVariable Integer fine_id)
    {
        fineService.deleteByFineId(fine_id);
    }

    /// ///


    @GetMapping("/find/fine_id/{fine_id}")
    public List<Fine> findByFineId(@PathVariable Integer fine_id)
    {
        return fineService.findByFineId(fine_id);
    }

    @GetMapping("/find/borrow_id_borrow/{borrow_id_borrow}")
    public List<Fine> findByBorrowIdBorrow(@PathVariable Integer borrow_id_borrow)
    {
        return fineService.findByBorrowIdBorrow(borrow_id_borrow);
    }

    //Same as on Service
    @GetMapping("/find/account_id/{account_id}")
    public List<Account> findByAccountId(@PathVariable Integer account_id)
    {
        return accountService.findByAccountId(account_id);
    }
}