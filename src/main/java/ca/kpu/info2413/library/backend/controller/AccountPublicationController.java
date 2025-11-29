package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.AccountPublication;
import ca.kpu.info2413.library.backend.service.AccountPublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/waitlist")
@CrossOrigin(origins = "*")
public class AccountPublicationController
{
    @Autowired
    private AccountPublicationService service;

    // Join waitlist (body: { "isbn": 978..., "accountId": 12 })
    @PostMapping
    public ResponseEntity<?> joinWaitlist(@RequestBody Map<String, String> body) {
        try {
            Long isbn = Long.parseLong(body.get("isbn"));
            Integer accountId = Integer.parseInt(body.get("accountId"));
            short pos = service.addToWaitlist(isbn, accountId);
            return ResponseEntity.status(201).body(Map.of("position", pos));
        } catch (NumberFormatException nfe) {
            return ResponseEntity.badRequest().body("isbn and accountId must be numbers.");
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error adding to waitlist.");
        }
    }

    // Get waitlist for a book (isbn)
    @GetMapping("/book/{isbn}")
    public ResponseEntity<?> getWaitlistForBook(@PathVariable Long isbn) {
        List<AccountPublication> list = service.getWaitlistForIsbn(isbn);
        return ResponseEntity.ok(list);
    }

    // Get waitlist items for an account
    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getWaitlistForAccount(@PathVariable Integer accountId) {
        List<AccountPublication> list = service.getWaitlistForAccount(accountId);
        return ResponseEntity.ok(list);
    }

    // Remove from waitlist (query params)
    @DeleteMapping
    public ResponseEntity<?> removeFromWaitlist(@RequestParam Long isbn, @RequestParam Integer accountId) {
        boolean ok = service.removeFromWaitlist(isbn, accountId);
        if (ok) return ResponseEntity.noContent().build();
        return ResponseEntity.status(404).body("Not found");
    }
}
