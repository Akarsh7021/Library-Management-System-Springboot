package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BookCopyConditionNotesRepository extends JpaRepository<BookCopyConditionNotes, Long> {

    List<BookCopyConditionNotes> findByCondition_notes(String condition_notes);
    List<BookCopyConditionNotes> findBySerial_barcode_BookCopy(int serial_barcode_BookCopy);

}
