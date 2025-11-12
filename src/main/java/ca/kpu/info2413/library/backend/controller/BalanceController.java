package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Balance;
import ca.kpu.info2413.library.backend.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceController
{
    @Autowired
    private BalanceService balanceService;

    @GetMapping
    public List<Balance> findAll()
    {
        return balanceService.findAll();
    }

    @GetMapping("/{user_id}")
    public List<Balance> findByUserId(@PathVariable Integer user_id)
    {
        return balanceService.findByAccountId(user_id);
    }

    // read-only
}
