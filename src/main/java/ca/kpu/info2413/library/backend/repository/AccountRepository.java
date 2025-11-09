package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface AccountRepository extends JpaRepository<Account, Integer>
{

    List<Account> findByAccountId(Integer accountId);

}