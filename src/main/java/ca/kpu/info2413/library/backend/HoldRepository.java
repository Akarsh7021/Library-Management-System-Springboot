package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface HoldRepository extends JpaRepository<Hold, Integer> {

    @Query("SELECT h FROM Hold h WHERE h.book_hold_id = :book_hold_id")
    List<Hold> findByBook_hold_id(@Param("book_hold_id") int book_hold_id);

}
