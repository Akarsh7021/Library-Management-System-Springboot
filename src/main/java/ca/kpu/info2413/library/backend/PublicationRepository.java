package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PublicationRepository extends JpaRepository<Publication, Integer> {

    @Query("SELECT p FROM Publication p WHERE p.isbn_13 = :isbn_13")
    List<Publication> findByIsbn_13(@Param("isbn_13") int isbn_13);

}
