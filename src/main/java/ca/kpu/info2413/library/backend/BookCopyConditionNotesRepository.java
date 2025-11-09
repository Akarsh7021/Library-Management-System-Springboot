package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BookCopyConditionNotesRepository extends JpaRepository<BookCopyConditionNotes, String>
{

    List<BookCopyConditionNotes> findByConditionNotes(String conditionNotes);

    List<BookCopyConditionNotes> findBySerialBarcodeBookCopy(Integer serialBarcodeBookCopy);

}
