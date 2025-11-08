package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BookCopyRepository extends JpaRepository<BookCopy, Integer> {

    List<BookCopy> findBySerialBarcode(Integer serialBarcode);
    List<BookCopy> findByIsbn13Publication(Integer isbn13Publication);
    List<BookCopy> findByDateAcquired(java.time.LocalDate dateAcquired);

}