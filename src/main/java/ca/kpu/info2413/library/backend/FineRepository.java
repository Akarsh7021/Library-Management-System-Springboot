package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface FineRepository extends JpaRepository<Fine, Integer> {

    @Query("SELECT f FROM Fine f WHERE f.fine_id = :fine_id")
    List<Fine> findByFine_id(@Param("fine_id") int fine_id);

}