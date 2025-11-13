package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController
{

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> findAll()
    {
        return accountService.findAll();
    }

    @GetMapping("/{account_id}")
    public List<Account> findByAccountId(@PathVariable Integer account_id)
    {
        return accountService.findByAccountId(account_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Account create(@RequestBody Account account)
    {
        return accountService.save(account);
    }

    @PutMapping
    public Account update(@RequestBody Account account)
    {
        return accountService.save(account);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{account_id}")
    public void deleteByAccountId(@PathVariable Integer account_id)
    {
        accountService.deleteByAccountId(account_id);
    }

    /// ///


    @GetMapping("/find/card_number/{card_number}")
    public List<Account> findByCardNumber(@PathVariable Integer card_number)
    {
        return accountService.findByLibraryCard(card_number);
    }

    @GetMapping("/find/full_name/{full_name}")
    public List<Account> findByFullName(@PathVariable String full_name)
    {
        return accountService.findByFullName(full_name);
    }

    @GetMapping("/find/notification_email/{notification_email}")
    public List<Account> findByNotificationEmail(@PathVariable String notification_email)
    {
        return accountService.findByNotificationEmail(notification_email);
    }

    @GetMapping("/find/phone_number/{phone_number}")
    public List<Account> findByPhoneNumber(@PathVariable String phone_number)
    {
        return accountService.findByPhoneNumber(phone_number);
    }


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

        System.out.println("Login attempt: email='" + email + "', password='" + password + "'");

        List<Account> accounts = accountService.findByNotificationEmail(email);

        if (accounts.isEmpty()) {
            System.out.println("No account found for email: " + email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        Account account = accounts.get(0);
        String dbPassword = account.getPasswordHash() != null ? account.getPasswordHash().trim() : "";

        System.out.println("DB password: '" + dbPassword + "'");

        if (!dbPassword.equals(password)) {
            System.out.println("Password mismatch");
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
