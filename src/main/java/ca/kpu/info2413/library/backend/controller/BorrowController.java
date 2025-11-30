package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.BookCopy;
import ca.kpu.info2413.library.backend.model.Borrow;
import ca.kpu.info2413.library.backend.repository.BookCopyRepository;
import ca.kpu.info2413.library.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.data.DefaultRepositoryTagsProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/borrow")
@CrossOrigin(origins = "*") // allow access from HTML/JS
public class BorrowController
{

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BookCopyService bookCopyService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private HoldService holdService;

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
        // check if book copy exists by looking up barcode
        Optional<BookCopy> bookCopy = bookCopyService.findBySerialBarcode(borrow.getSerialBarcodeBookCopy());

        if (bookCopy.isEmpty())
        {
            return ResponseEntity.badRequest().body(String.format("Book copy %s not Found!", borrow.getSerialBarcodeBookCopy()));
        }

        // get book title from publication
        String bookName = bookCopy.get().getPublication().getTitle();

        // get the latest borrow entry of the book copy
        Borrow b = null;

        try
        {
            b = findBySerialBarcodeBookCopy(borrow.getSerialBarcodeBookCopy()).getLast();
        }
        catch (Exception _)
        {
        }

        // check if book is already borrowed
        if ((b != null) && Objects.equals(b.getStatus(), "Borrowed"))
        {
            return ResponseEntity.badRequest().body(String.format("The book \"%s\" is not available for borrowing.", bookName));
        }

        // don't loan to anyone but the account with a hold, if applicable
        assert b != null;
        // if (the list of holds (should be only one ever) for this book copy is NOT empty) AND (the hold for this bookcopy has property AccountIdAccount that does NOT match the same property of the new borrow), return an error
        if (!holdService.findBySerialBarcodeBookCopy(b.getSerialBarcodeBookCopy()).isEmpty() && !Objects.equals(holdService.findBySerialBarcodeBookCopy(b.getSerialBarcodeBookCopy()).getFirst().getAccountIdAccount(), borrow.getAccountIdAccount()))
            return ResponseEntity.badRequest().body(String.format("The book \"%s\" is held for account \"%s\".", b.getSerialBarcodeBookCopy(),  holdService.findBySerialBarcodeBookCopy(b.getSerialBarcodeBookCopy()).getFirst().getAccountIdAccount()));

        // check if account exists
        Account borrowAccount;

        try
        {
            borrowAccount = accountService.findByAccountId(borrow.getAccountIdAccount()).getFirst();
        }
        catch (Exception _)
        {
            return ResponseEntity.badRequest().body(String.format("Account ID %s not found.", borrow.getAccountIdAccount()));
        }

        // add borrow record
        borrowService.save(borrow);

        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Book \"%s\" successfully borrowed by %s.", bookName, borrowAccount.getFullName()));
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