package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.LibraryCard;
import ca.kpu.info2413.library.backend.service.AccountService;
import ca.kpu.info2413.library.backend.service.LibraryCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RegistrationController
{
    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private LibraryCardService libraryCardService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData)
    {
        try
        {
            // read both camelCase and lowercase keys (tolerant)
            String fullName = trim(userData.getOrDefault("fullName", userData.get("fullname")));
            String email = trim(userData.getOrDefault("email", userData.get("notificationEmail")));
            String password = userData.get("password");
            String confirmPassword = userData.get("confirmPassword");
            String phone = trim(userData.getOrDefault("phone", userData.get("phoneNumber")));
            String libraryCardStr = trim(userData.get("libraryCard"));

            logger.info("Register request for email={}, fullname={}, libraryCard={}", email, fullName, libraryCardStr);

            // required fields
            if (fullName == null || email == null || password == null || confirmPassword == null)
            {
                return ResponseEntity.badRequest().body("Required fields missing: fullName, email, password, confirmPassword.");
            }

            if (!password.equals(confirmPassword))
            {
                return ResponseEntity.badRequest().body("Passwords do not match.");
            }

            // duplicate email check (case-insensitive)
            if (!accountService.findByNotificationEmailIgnoreCase(email).isEmpty())
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered.");
            }

            // create account (accountType forced to MEMBER for self-registration)
            Account account = new Account();
            account.setFullName(fullName);
            account.setNotificationEmail(email);
            account.setPasswordHash(password); // NOTE: hash in production
            account.setPhoneNumber(phone);
            account.setAccountType("MEMBER");

            Account savedAccount = accountService.save(account);
            logger.info("Saved account id={} email={}", savedAccount.getAccountId(), savedAccount.getNotificationEmail());

            // If client provided a libraryCard value, attempt to link it.
            if (libraryCardStr != null && !libraryCardStr.isBlank())
            {
                Integer libraryCardId;
                try
                {
                    libraryCardId = Integer.parseInt(libraryCardStr.trim());
                }
                catch (NumberFormatException nfe)
                {
                    // cleanup - optionally delete the saved account? Here we return an error so caller can fix the card id.
                    logger.warn("Invalid libraryCard provided: {}", libraryCardStr);
                    return ResponseEntity.badRequest().body("Library Card ID must be a number.");
                }

                List<LibraryCard> cards = libraryCardService.findByCardNumber(libraryCardId);
                if (cards == null || cards.isEmpty())
                {
                    // card not found — we return bad request so client can correct it.
                    return ResponseEntity.badRequest().body("Library card not found.");
                }

                LibraryCard card = cards.get(0);
                if (card.getAccount() != null)
                {
                    return ResponseEntity.badRequest().body("Library card is already linked to another account.");
                }

                card.setAccount(savedAccount);
                libraryCardService.save(card);
                logger.info("Linked library card {} to account {}", libraryCardId, savedAccount.getAccountId());
            }

            // return the saved account (or minimal success message)
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
        }
        catch (Exception ex)
        {
            logger.error("Error during registration", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }

    private String trim(String s) {
        return s == null ? null : s.trim();
    }
}