package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.BookCopy;
import ca.kpu.info2413.library.backend.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BookCopyRepository extends JpaRepository<BookCopy, Integer>
{

    Optional<BookCopy> findBySerialBarcode(Integer serialBarcode);

    List<BookCopy> findByPublication(Publication publication);

        @Query("select bc from BookCopy bc " +
           "where bc.publication.isbn13 = :isbn " +
           "and bc.serialBarcode not in (select h.serialBarcodeBookCopy from Hold h) " +
           "and bc.serialBarcode not in (select b.serialBarcodeBookCopy from Borrow b where b.returnedDate is null)")
    List<BookCopy> findAvailableCopiesByIsbn(@Param("isbn") Long isbn);


}