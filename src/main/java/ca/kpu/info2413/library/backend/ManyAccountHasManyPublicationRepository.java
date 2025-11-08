package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface ManyAccountHasManyPublicationRepository extends JpaRepository<ManyAccountHasManyPublication, Integer> {

    @Query("SELECT m FROM ManyAccountHasManyPublication m WHERE m.waitlist_position = :waitlist_position")
    List<ManyAccountHasManyPublication> findByWaitlist_position(@Param("waitlist_position") int waitlist_position);

    @Query("SELECT m FROM ManyAccountHasManyPublication m WHERE m.account_id_Account = :account_id_Account")
    List<ManyAccountHasManyPublication> findByAccount_id_Account(@Param("account_id_Account") int account_id_Account);

    @Query("SELECT m FROM ManyAccountHasManyPublication m WHERE m.isbn_13_Publication = :isbn_13_Publication")
    List<ManyAccountHasManyPublication> findByIsbn_13_Publication(@Param("isbn_13_Publication") int isbn_13_Publication);

}
