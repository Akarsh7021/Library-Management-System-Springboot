package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.LibraryCard;
import ca.kpu.info2413.library.backend.service.AccountService;
import ca.kpu.info2413.library.backend.service.LibraryCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library_card")
public class LibraryCardController
{

    @Autowired
    private LibraryCardService libraryCardService;

    @GetMapping
    public List<LibraryCard> findAll()
    {
        return libraryCardService.findAll();
    }

    @GetMapping("/{card_number}")
    public List<LibraryCard> findByCardNumber(@PathVariable Integer card_number)
    {
        return libraryCardService.findByCardNumber(card_number);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public LibraryCard create(@RequestBody LibraryCard libraryCard)
    {
        return libraryCardService.save(libraryCard);
    }

    @PutMapping
    public LibraryCard update(@RequestBody LibraryCard libraryCard)
    {
        return libraryCardService.save(libraryCard);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{card_number}")
    public void deleteByCardNumber(@PathVariable Integer card_number)
    {
        LibraryCardService.deleteByCardNumber(card_number);
    }

    /// ///


    @GetMapping("/find/card_number/{card_number}")
    public List<LibraryCard> findByCardNumber(@PathVariable Integer card_number)
    {
        return libraryCardService.findByCardNumber(card_number);
    }

    @GetMapping("/find/account_id_account/{account_id_account}")
    public List<LibraryCard> findByAccountIdAccount(@PathVariable Integer account_id_account)
    {
        return libraryCardService.findByAccountIdAccount(account_id_account);
    }

}

