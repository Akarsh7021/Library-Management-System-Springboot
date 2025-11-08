package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BookCopyConditionNotesRepository extends JpaRepository<BookCopyConditionNotes, String> {

    @Query("SELECT c FROM BookCopyConditionNotes c WHERE c.condition_notes = :condition_notes")
    List<BookCopyConditionNotes> findByCondition_notes(@Param("condition_notes") String condition_notes);

    @Query("SELECT c FROM BookCopyConditionNotes c WHERE c.serial_barcode_BookCopy = :serial_barcode_BookCopy")
    List<BookCopyConditionNotes> findBySerial_barcode_BookCopy(@Param("serial_barcode_BookCopy") int serial_barcode_BookCopy);

}
