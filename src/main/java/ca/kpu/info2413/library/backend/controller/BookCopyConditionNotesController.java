package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.BookCopy;
import ca.kpu.info2413.library.backend.model.BookCopyConditionNotes;
import ca.kpu.info2413.library.backend.service.BookCopyConditionNotesService;
import ca.kpu.info2413.library.backend.service.BookCopyService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book_copy_condition_notes")
public class BookCopyConditionNotesController
{

    @Autowired
    private BookCopyConditionNotesService bookCopyConditionNotesService;
    @Autowired
    private BookCopyService bookCopyService;

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
    public ResponseEntity<?> create(@RequestBody BookCopyConditionNotes bookCopyConditionNotes)
    {
        Optional<BookCopy> bc = bookCopyService.findBySerialBarcode(bookCopyConditionNotes.getSerialBarcodeBookCopy());
        if(bc.isEmpty())
        {
            return ResponseEntity.badRequest().body("Serial Barcode not found.");
        }

        return ResponseEntity.ok(bookCopyConditionNotesService.save(bookCopyConditionNotes));
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

