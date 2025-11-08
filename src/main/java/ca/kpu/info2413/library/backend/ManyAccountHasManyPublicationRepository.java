package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface ManyAccountHasManyPublicationRepository extends JpaRepository<ManyAccountHasManyPublication, Integer> {

    List<ManyAccountHasManyPublication> findByWaitlistPosition(Integer waitlistPosition);

    List<ManyAccountHasManyPublication> findByAccountIdAccount(Integer accountIdAccount);

    List<ManyAccountHasManyPublication> findByIsbn13Publication(Integer isbn13Publication);

}
