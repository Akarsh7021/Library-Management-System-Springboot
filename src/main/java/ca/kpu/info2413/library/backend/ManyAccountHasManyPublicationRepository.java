package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface ManyAccountHasManyPublicationRepository extends JpaRepository<ManyAccountHasManyPublication, ManyAccountHasManyPublication.ManyAccountHasManyPublicationId> {

    List<ManyAccountHasManyPublication> findByWaitlistPosition(Short waitlistPosition);

    List<ManyAccountHasManyPublication> findByAccountIdAccount(Integer accountIdAccount);

    List<ManyAccountHasManyPublication> findByIsbn13Publication(Integer isbn13Publication);

    List<ManyAccountHasManyPublication> findByAccountIdAccountAndIsbn13Publication(Integer accountIdAccount, Integer isbn13Publication);

}
