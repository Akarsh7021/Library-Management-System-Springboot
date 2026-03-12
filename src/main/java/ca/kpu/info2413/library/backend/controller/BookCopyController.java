package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.DTO.PublicationDTO;
import ca.kpu.info2413.library.backend.model.BookCopy;
import ca.kpu.info2413.library.backend.model.Publication;
import ca.kpu.info2413.library.backend.service.BookCopyService;
import ca.kpu.info2413.library.backend.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book_copy")
public class BookCopyController
{

    @Autowired
    private BookCopyService bookCopyService;
    @Autowired
    private PublicationService publicationService;

    @GetMapping
    public List<BookCopy> findAll()
    {
        return bookCopyService.findAll();
    }

    @GetMapping("/{serial_barcode}")
    public Optional<BookCopy> findBySerialBarcode(@PathVariable Integer serial_barcode)
    {
        return bookCopyService.findBySerialBarcode(serial_barcode);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookCopy bookCopy)
    {
        List<PublicationDTO> p = publicationService.findByIsbn13(bookCopy.getPublication().getIsbn13());
        if(p.isEmpty())
        {
            return ResponseEntity.badRequest().body("Publication not found");
        }

        if (p.getFirst().getEbookUrl() == null || p.getFirst().getEbookUrl().isEmpty())
            return ResponseEntity.ok(bookCopyService.save(bookCopy));
        else
            return ResponseEntity.badRequest().body("Cannot create book copy. Publication is an Ebook.");

    }

    @PutMapping
    public BookCopy update(@RequestBody BookCopy bookCopy)
    {
        return bookCopyService.save(bookCopy);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{serial_barcode}")
    public void deleteBySerialBarcode(@PathVariable Integer serial_barcode)
    {
        bookCopyService.deleteBySerialBarcode(serial_barcode);
    }

    /// ///


    @GetMapping("/find/isbn/{isbn}")
    public List<BookCopy> findByIsbn13(@PathVariable Long isbn)
    {
        return bookCopyService.findByIsbn13(isbn);
    }
}
