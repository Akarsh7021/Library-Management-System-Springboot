package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    List<BookCopy> findBySerial_barcode(int serial_barcode);

}