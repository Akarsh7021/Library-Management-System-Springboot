package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.LibraryCard;
import ca.kpu.info2413.library.backend.service.AccountService;
import ca.kpu.info2413.library.backend.service.LibraryCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RegistrationController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LibraryCardService libraryCardService;

    /**
     * Expects JSON:
     * {
     *   "fullName": "...",
     *   "email": "...",
     *   "password": "...",
     *   "confirmPassword": "...",
     *   "phone": "...",
     *   "libraryCard": "123"
     * }
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData) {
        try {
            String fullName = userData.get("fullName");
            String email = userData.get("email");
            String password = userData.get("password");
            String confirmPassword = userData.get("confirmPassword");
            String phone = userData.get("phone");
            String libraryCardStr = userData.get("libraryCard");

            // basic validation
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

            // find library card
            List<LibraryCard> cards = libraryCardService.findByCardNumber(libraryCardId);
            if (cards == null || cards.isEmpty()) {
                return ResponseEntity.badRequest().body("Library card not found.");
            }

            LibraryCard card = cards.get(0);

            // IMPORTANT: check relational field (Account object) not a numeric id
            if (card.getAccount() != null) {
                return ResponseEntity.badRequest().body("Library card is already linked to an account.");
            }

            // create and save account
            Account account = new Account();
            account.setFullName(fullName);
            account.setNotificationEmail(email);
            account.setPasswordHash(password); // plain-text for now; replace with hashing in prod
            account.setPhoneNumber(phone);
            account.setAccountType("MEMBER");

            Account savedAccount = accountService.save(account);

            // link the library card to the saved account and persist the card
            card.setAccount(savedAccount);
            libraryCardService.save(card);

            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }
}