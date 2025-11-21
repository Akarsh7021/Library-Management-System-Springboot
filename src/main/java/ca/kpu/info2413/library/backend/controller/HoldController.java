package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Hold;
import ca.kpu.info2413.library.backend.service.HoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/hold")
public class HoldController
{

    @Autowired
    private HoldService holdService;

    @GetMapping
    public List<Hold> findAll()
    {
        return holdService.findAll();
    }

    @GetMapping("/{book_hold_id}")
    public List<Hold> findByBookHoldId(@PathVariable Integer book_hold_id)
    {
        return holdService.findByBookHoldId(book_hold_id);
    }


    /// ///


    @GetMapping("/find/serial_barcode_bookcopy/{serial_barcode_bookcopy}")
    public List<Hold> findBySerialBarcodeBookCopy(@PathVariable Integer serial_barcode_bookcopy)
    {
        return holdService.findBySerialBarcodeBookCopy(serial_barcode_bookcopy);
    }

    @GetMapping("/find/account_id/{account_id}")
    public List<Hold> findByAccountId(@PathVariable Integer account_id)
    {
        return holdService.findByAccountId(account_id);
    }

    @PostMapping
    public ResponseEntity<?> createHold(@RequestBody Map<String, String> body)
    {
        try
        {
            String bookIdStr = body.get("bookId");
            String accountIdStr = body.get("accountId");
            if (bookIdStr == null || accountIdStr == null)
            {
                return ResponseEntity.badRequest().body("bookId and accountId are required.");
            }
            Integer bookId = Integer.parseInt(bookIdStr);
            Integer accountId = Integer.parseInt(accountIdStr);

            Optional<Hold> exists = holdService.findByBookAndAccount(bookId, accountId);
            if (exists.isPresent())
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Hold already exists for that book/account.");
            }

            Hold created = holdService.createHold(bookId, accountId);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }
        catch (NumberFormatException nfe)
        {
            return ResponseEntity.badRequest().body("bookId and accountId must be numbers.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating hold.");
        }
    }

    /**
     * Cancel a hold by bookId and accountId
     * DELETE /holds?bookId=123&accountId=456
     */
    @DeleteMapping
    public ResponseEntity<?> cancelHold(@RequestParam Integer bookId, @RequestParam Integer accountId)
    {
        boolean removed = holdService.cancelHold(bookId, accountId);
        if (removed)
        {
            return ResponseEntity.noContent().build();
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hold not found for that book/account.");
        }
    }
}