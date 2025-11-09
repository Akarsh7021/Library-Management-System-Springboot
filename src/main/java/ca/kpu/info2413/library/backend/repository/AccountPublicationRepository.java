package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.AccountPublication;
import ca.kpu.info2413.library.backend.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface AccountPublicationRepository extends JpaRepository<AccountPublication, Integer>
{

    List<AccountPublication> findByWaitlistPosition(Integer waitlistPosition);

    List<AccountPublication> findByAccount(Account account);

    List<AccountPublication> findByPublication(Publication publication);

}
