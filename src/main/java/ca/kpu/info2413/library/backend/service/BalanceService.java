package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Balance;
import ca.kpu.info2413.library.backend.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceService
{
    @Autowired
    BalanceRepository balanceRepository;

    public List<Balance> findAll()
    {
        return balanceRepository.findAll();
    }

    public List<Balance> findByAccountId(Integer accountId)
    {
        return balanceRepository.findByAccountId(accountId);
    }

    // read-only


}
