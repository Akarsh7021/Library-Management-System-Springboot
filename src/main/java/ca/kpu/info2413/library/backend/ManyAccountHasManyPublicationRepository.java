package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface ManyAccountHasManyPublicationRepository extends JpaRepository<ManyAccountHasManyPublication, Long> {

    List<ManyAccountHasManyPublication> findByWaitlist_position(int waitlist_position);
    List<ManyAccountHasManyPublication> findByAccount_id_Account(int account_id_Account);
    List<ManyAccountHasManyPublication> findByIsbn_13_Publication(int isbn_13_Publication);


}
