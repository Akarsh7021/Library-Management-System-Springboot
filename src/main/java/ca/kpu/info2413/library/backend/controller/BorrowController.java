package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Borrow;
import ca.kpu.info2413.library.backend.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/borrow")
@CrossOrigin(origins = "*") // allow access from HTML/JS
public class BorrowController
{

    @Autowired
    private BorrowService borrowService;

    @GetMapping
    public List<Borrow> findAll()
    {
        return borrowService.findAll();
    }

    @GetMapping("/{borrow_id}")
    public List<Borrow> findByBorrowId(@PathVariable Integer borrow_id)
    {
        return borrowService.findByBorrowId(borrow_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Borrow borrow)
    {
        Borrow b = findBySerialBarcodeBookCopy(borrow.getSerialBarcodeBookCopy()).getLast();

        if((b != null) && Objects.equals(b.getStatus(), "Borrowed")){
            return ResponseEntity.badRequest().body("Book is not available");
        }

        borrowService.save(borrow);

        return ResponseEntity.status(HttpStatus.CREATED).body("Book borrowed successfully.");
    }

    @PutMapping
    public Borrow update(@RequestBody Borrow borrow)
    {
        return borrowService.save(borrow);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{borrow_id}")
    public void deleteByBorrowId(@PathVariable Integer borrow_id)
    {
        borrowService.deleteByBorrowId(borrow_id);
    }

    @GetMapping("/find/serial_barcode_bookcopy/{serial_barcode_bookcopy}")
    public List<Borrow> findBySerialBarcodeBookCopy(@PathVariable Integer serial_barcode_bookcopy)
    {
        return borrowService.findBySerialBarcodeBookCopy(serial_barcode_bookcopy);
    }

    @GetMapping("/find/account_id_account/{account_id_account}")
    public List<Borrow> findByAccountIdAccount(@PathVariable Integer account_id_account)
    {
        return borrowService.findByAccountIdAccount(account_id_account);
    }
}