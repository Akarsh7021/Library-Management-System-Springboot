package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.LibraryCard;
import ca.kpu.info2413.library.backend.security.AccountUserDetails;
import ca.kpu.info2413.library.backend.service.AccountService;
import ca.kpu.info2413.library.backend.service.LibraryCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LibraryCardService libraryCardService;

    // Get all accounts
    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }

    // Get account by id
    @GetMapping("/{account_id}")
    public ResponseEntity<?> findByAccountId(@PathVariable Integer account_id) {
        List<Account> accounts = accountService.findByAccountId(account_id);
        if (accounts == null || accounts.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        return ResponseEntity.ok(accounts.get(0));
    }

    // Create (internal)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Account create(@RequestBody Account account) {
        return accountService.save(account);
    }

    // Update - preserve accountType unless provided; only update password if non-empty
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Account incoming) {
        if (incoming == null || incoming.getAccountId() == null) {
            return ResponseEntity.badRequest().body("Account ID is required for update");
        }

        List<Account> existingList = accountService.findByAccountId(incoming.getAccountId());
        if (existingList == null || existingList.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");

        Account existing = existingList.get(0);

        if (incoming.getFullName() != null) existing.setFullName(incoming.getFullName());
        if (incoming.getNotificationEmail() != null) existing.setNotificationEmail(incoming.getNotificationEmail());
        if (incoming.getPhoneNumber() != null) existing.setPhoneNumber(incoming.getPhoneNumber());

        if (incoming.getAccountType() != null) existing.setAccountType(incoming.getAccountType());

        if (incoming.getPasswordHash() != null && !incoming.getPasswordHash().trim().isEmpty())
            existing.setPasswordHash(incoming.getPasswordHash());

        Account saved = accountService.save(existing);
        return ResponseEntity.ok(saved);
    }

    // Delete account and unlink any library cards belonging to it
    @DeleteMapping("/{account_id}")
    public ResponseEntity<?> deleteByAccountId(@PathVariable Integer account_id, HttpServletRequest request) {
        List<Account> existing = accountService.findByAccountId(account_id);
        if (existing == null || existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        // unlink library cards that reference this account
        List<LibraryCard> cards = libraryCardService.findByAccountIdAccount(account_id);
        if (cards != null) {
            for (LibraryCard c : cards) {
                c.setAccount(null);
                libraryCardService.save(c);
            }
        }

        accountService.deleteByAccountId(account_id);

        // Invalidate session & security context to log out
        try { if (request != null && request.getSession(false) != null) request.getSession(false).invalidate(); } catch (Exception ignored) {}
        try { SecurityContextHolder.clearContext(); } catch (Exception ignored) {}
        return ResponseEntity.noContent().build();
    }

    // Find account by library card number
    @GetMapping("/find/card_number/{card_number}")
    public ResponseEntity<?> findByCardNumber(@PathVariable Integer card_number) {
        Optional<Account> opt = accountService.findByLibraryCard(card_number);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found for this card number");
        return ResponseEntity.ok(opt.get());
    }

    // Other search endpoints
    @GetMapping("/find/full_name/{full_name}")
    public List<Account> findByFullName(@PathVariable String full_name) {
        return accountService.findByFullName(full_name);
    }

    @GetMapping("/find/notification_email/{notification_email}")
    public List<Account> findByNotificationEmail(@PathVariable String notification_email) {
        return accountService.findByNotificationEmail(notification_email);
    }

    @GetMapping("/find/phone_number/{phone_number}")
    public List<Account> findByPhoneNumber(@PathVariable String phone_number) {
        return accountService.findByPhoneNumber(phone_number);
    }

    // Get current logged-in account (for edit page)
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentAccount(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        Object principal = authentication.getPrincipal();
        Account account = null;

        if (principal instanceof AccountUserDetails) {
            account = ((AccountUserDetails) principal).getAccount();
        } else {
            // fallback: search by username/email
            List<Account> matches = accountService.findByNotificationEmailIgnoreCase(authentication.getName());
            if (matches != null && !matches.isEmpty()) account = matches.get(0);
        }

        if (account == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");

        Map<String, Object> response = new HashMap<>();
        response.put("accountId", account.getAccountId());
        response.put("fullName", account.getFullName());
        response.put("notificationEmail", account.getNotificationEmail());
        response.put("phoneNumber", account.getPhoneNumber());
        response.put("accountType", account.getAccountType());

        return ResponseEntity.ok(response);
    }

    // Simple login (kept for compatibility)
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("notificationEmail");
        String password = credentials.get("password");

        if (email == null || password == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and password must be provided.");

        List<Account> accounts = accountService.findByNotificationEmailIgnoreCase(email.trim());
        if (accounts.isEmpty() || !accounts.get(0).getPasswordHash().equals(password.trim())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        Account account = accounts.get(0);
        Map<String, Object> response = new HashMap<>();
        response.put("accountId", account.getAccountId());
        response.put("fullName", account.getFullName());
        response.put("email", account.getNotificationEmail());
        response.put("accountType", account.getAccountType());
        return ResponseEntity.ok(response);
    }

    // === Registration endpoint ===
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData) {
        try {
            String fullName = userData.get("fullName");
            String email = userData.get("email");
            String password = userData.get("password");
            String confirmPassword = userData.get("confirmPassword");
            String phone = userData.get("phone");
            String libraryCardStr = userData.get("libraryCard");

            if (fullName == null || email == null || password == null || confirmPassword == null ||
                    phone == null || libraryCardStr == null) {
                return ResponseEntity.badRequest().body("All fields are required.");
            }

            if (!password.equals(confirmPassword)) {
                return ResponseEntity.badRequest().body("Passwords do not match.");
            }

            Integer libraryCardId;
            try {
                libraryCardId = Integer.parseInt(libraryCardStr);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Library Card ID must be a number.");
            }

            // check duplicate email
            if (!accountService.findByNotificationEmailIgnoreCase(email).isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered.");
            }

            // find card
            List<LibraryCard> cards = libraryCardService.findByCardNumber(libraryCardId);
            if (cards == null || cards.isEmpty()) {
                return ResponseEntity.badRequest().body("Library card not found.");
            }

            LibraryCard card = cards.get(0);
            if (card.getAccount() != null) {
                return ResponseEntity.badRequest().body("Library card is already linked to an account.");
            }

            // create and save account
            Account account = new Account();
            account.setFullName(fullName);
            account.setNotificationEmail(email);
            account.setPasswordHash(password); // plain-text for now; replace with hashing later
            account.setPhoneNumber(phone);
            account.setAccountType("MEMBER");

            Account savedAccount = accountService.save(account);

            // link card to saved account and save card
            card.setAccount(savedAccount);
            libraryCardService.save(card);

            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }
}