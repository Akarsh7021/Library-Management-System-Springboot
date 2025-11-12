package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Hold;
import ca.kpu.info2413.library.backend.service.HoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Hold create(@RequestBody Hold hold)
    {
        return holdService.save(hold);
    }

    @PutMapping
    public Hold update(@RequestBody Hold hold)
    {
        return holdService.save(hold);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{book_hold_id}")
    public void deleteByBookHoldId(@PathVariable Integer book_hold_id)
    {
        holdService.deleteByBookHoldId(book_hold_id);
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
}