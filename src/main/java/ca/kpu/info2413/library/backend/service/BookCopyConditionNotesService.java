package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.BookCopyConditionNotes;
import ca.kpu.info2413.library.backend.repository.BookCopyConditionNotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCopyConditionNotesService
{
    @Autowired
    BookCopyConditionNotesRepository bookCopyConditionNotesRepository;


    public List<BookCopyConditionNotes> findAll()
    {
        return bookCopyConditionNotesRepository.findAll();
    }

    public List<BookCopyConditionNotes> findBySerialBarcodeBookCopy(Integer serialBarcodeBookCopy)
    {
        return bookCopyConditionNotesRepository.findBySerialBarcodeBookCopy(serialBarcodeBookCopy);
    }

    public BookCopyConditionNotes save(BookCopyConditionNotes bookCopyConditionNotes)
    {
        return bookCopyConditionNotesRepository.save(bookCopyConditionNotes);
    }

    public void deleteBySerialBarcodeBookCopy(Integer serialBarcodeBookCopy)
    {
        BookCopyConditionNotes.deleteBySerialBarcodeBookCopy(serialBarcodeBookCopy);
    }


}
