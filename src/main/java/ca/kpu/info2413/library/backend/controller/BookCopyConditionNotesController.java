package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.BookCopyConditionNotes;
import ca.kpu.info2413.library.backend.service.AccountService;
import ca.kpu.info2413.library.backend.service.BookCopyConditionNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book_copy_condition_notes")
public class BookCopyConditionNotesController
{

    @Autowired
    private BookCopyConditionNotesService bookCopyConditionNotesService;

    @GetMapping
    public List<BookCopyConditionNotes> findAll()
    {
        return bookCopyConditionNotesService.findAll();
    }

    @GetMapping("/{serial_barcode_bookcopy}")
    public List<BookCopyConditionNotes> findBySerialBarcodeBookCopy(@PathVariable Integer serial_barcode_bookcopy)
    {
        return bookCopyConditionNotesService.findBySerialBarcodeBookCopy(serial_barcode_bookcopy);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookCopyConditionNotes create(@RequestBody BookCopyConditionNotes bookCopyConditionNotes)
    {
        return bookCopyConditionNotesService.save(bookCopyConditionNotes);
    }

    @PutMapping
    public BookCopyConditionNotes update(@RequestBody BookCopyConditionNotes bookCopyConditionNotes)
    {
        return bookCopyConditionNotesService.save(bookCopyConditionNotes);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{serial_barcode_bookcopy}")
    public void deleteBySerialBarcodeBookCopy(@PathVariable Integer serial_barcode_bookcopy)
    {
        bookCopyConditionNotesService.deleteBySerialBarcodeBookCopy(serial_barcode_bookcopy);
    }

}

