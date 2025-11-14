package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.security.AccountUserDetails;
import ca.kpu.info2413.library.backend.service.AccountService;
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

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/{account_id}")
    public List<Account> findByAccountId(@PathVariable Integer account_id) {
        return accountService.findByAccountId(account_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Account create(@RequestBody Account account) {
        return accountService.save(account);
    }

    /**
     * Update account: preserve accountType if incoming payload doesn't include it.
     * Only overwrite passwordHash if a non-empty password is provided.
     */
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Account incoming) {
        if (incoming == null) {
            return ResponseEntity.badRequest().body("No account data provided");
        }

        Integer id = incoming.getAccountId();
        if (id == null) {
            return ResponseEntity.badRequest().body("accountId is required for update");
        }

        List<Account> existingList = accountService.findByAccountId(id);
        if (existingList == null || existingList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        Account existing = existingList.get(0);

        // Update fields if provided
        if (incoming.getFullName() != null) existing.setFullName(incoming.getFullName());
        if (incoming.getNotificationEmail() != null) existing.setNotificationEmail(incoming.getNotificationEmail());
        if (incoming.getPhoneNumber() != null) existing.setPhoneNumber(incoming.getPhoneNumber());

        // Preserve accountType if incoming doesn't provide it
        if (incoming.getAccountType() != null) {
            existing.setAccountType(incoming.getAccountType());
        } // else keep existing.getAccountType()

        // Only update passwordHash if a non-empty value was provided
        if (incoming.getPasswordHash() != null && !incoming.getPasswordHash().trim().isEmpty()) {
            existing.setPasswordHash(incoming.getPasswordHash());
        }

        Account saved = accountService.save(existing);
        return ResponseEntity.ok(saved);
    }

    /**
     * Delete account and invalidate session/security context so deleted user is logged out.
     */
    @DeleteMapping("/{account_id}")
    public ResponseEntity<?> deleteByAccountId(@PathVariable Integer account_id, HttpServletRequest request) {
        // confirm exists
        List<Account> existing = accountService.findByAccountId(account_id);
        if (existing == null || existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        accountService.deleteByAccountId(account_id);

        // Invalidate session and clear security context to log user out
        try {
            // invalidate HTTP session
            if (request != null && request.getSession(false) != null) {
                request.getSession(false).invalidate();
            }
        } catch (Exception ignored) {}

        try {
            // clear Spring Security context
            SecurityContextHolder.clearContext();
        } catch (Exception ignored) {}

        return ResponseEntity.noContent().build();
    }

    // Helpers to search by properties (kept existing endpoints)
    @GetMapping("/find/card_number/{card_number}")
    public List<Account> findByCardNumber(@PathVariable Integer card_number) {
        return accountService.findByLibraryCard(card_number);
    }

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

    /**
     * Return the currently authenticated account details (for the edit page).
     * Be defensive about the type of principal (AccountUserDetails or generic User).
     */
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
            // fallback: find by username/email (Authentication#getName())
            String username = authentication.getName();
            List<Account> matches = accountService.findByNotificationEmailIgnoreCase(username);
            if (matches != null && !matches.isEmpty()) account = matches.get(0);
        }

        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("accountId", account.getAccountId());
        response.put("fullName", account.getFullName());
        response.put("notificationEmail", account.getNotificationEmail());
        response.put("phoneNumber", account.getPhoneNumber());
        response.put("accountType", account.getAccountType());

        return ResponseEntity.ok(response);
    }

    // Simple login endpoint (kept from your previous code)
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("notificationEmail");
        String password = credentials.get("password");

        if (email == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email and password must be provided.");
        }

        email = email.trim();
        password = password.trim();

        List<Account> accounts = accountService.findByNotificationEmail(email);
        if (accounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        Account account = accounts.get(0);
        String dbPassword = account.getPasswordHash() != null ? account.getPasswordHash().trim() : "";
        if (!dbPassword.equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("accountId", account.getAccountId());
        response.put("fullName", account.getFullName());
        response.put("email", account.getNotificationEmail());
        response.put("accountType", account.getAccountType());

        return ResponseEntity.ok(response);
    }
}