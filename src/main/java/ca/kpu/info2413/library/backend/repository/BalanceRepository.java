package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Integer>
{
    List<Balance> findByAccountId(Integer accountId);
}
