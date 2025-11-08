package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

    @Query("SELECT b FROM Borrow b WHERE b.borrow_id = :borrow_id")
    List<Borrow> findByBorrow_id(@Param("borrow_id") int borrow_id);

}
