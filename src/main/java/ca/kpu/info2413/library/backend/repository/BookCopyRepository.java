package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.BookCopy;
import ca.kpu.info2413.library.backend.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BookCopyRepository extends JpaRepository<BookCopy, Integer>
{

    Optional<BookCopy> findBySerialBarcode(Integer serialBarcode);

    List<BookCopy> findByPublication(Publication publication);

}