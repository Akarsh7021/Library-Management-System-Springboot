package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.DTO.PublicationDTO;
import ca.kpu.info2413.library.backend.model.*;
import ca.kpu.info2413.library.backend.security.AccountUserDetails;
import ca.kpu.info2413.library.backend.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
public class AccountController
{
    // to validate pw
    String passwordPolicy = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;
    @Autowired
    BookCopyService bookCopyService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private LibraryCardService libraryCardService;
    @Autowired
    private PublicationService publicationService;
    @Autowired
    private FineService fineService;

    // Get all accounts
    @GetMapping
    public List<Account> findAll()
    {
        return accountService.findAll();
    }

    // Get account by id
    @GetMapping("/{account_id}")
    public ResponseEntity<?> findByAccountId(@PathVariable Integer account_id)
    {
        List<Account> accounts = accountService.findByAccountId(account_id);
        if (accounts == null || accounts.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        return ResponseEntity.ok(accounts.getFirst());
    }

    // Create (internal)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Account create(@RequestBody Account account)
    {
        return accountService.save(account);
    }

    // Update - preserve accountType unless provided; only update password if non-empty
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Account incoming)
    {
        if (incoming == null || incoming.getAccountId() == null)
        {
            return ResponseEntity.badRequest().body("Account ID is required for update");
        }

        List<Account> existingList = accountService.findByAccountId(incoming.getAccountId());
        if (existingList == null || existingList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");

        Account existing = existingList.getFirst();

        if (incoming.getFullName() != null) existing.setFullName(incoming.getFullName());
        if (incoming.getNotificationEmail() != null) existing.setNotificationEmail(incoming.getNotificationEmail());
        if (incoming.getPhoneNumber() != null) existing.setPhoneNumber(incoming.getPhoneNumber());

        if (incoming.getAccountType() != null) existing.setAccountType(incoming.getAccountType());

        if (incoming.getPasswordHash() != null && !incoming.getPasswordHash().trim().isEmpty()) {
            // validate password before calling Service to hash
            if(incoming.getPasswordHash().matches(passwordPolicy))
                existing.setPasswordHash(incoming.getPasswordHash());
            else return ResponseEntity.badRequest().body("Password must contain at least 8 characters with at least 1 letter and 1 number");
        }

        Account saved = accountService.save(existing);
        return ResponseEntity.ok(saved);
    }

    /**
     * Delete account by id.
     * IMPORTANT: only invalidate the current session if the deleted account equals the currently authenticated account.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Integer id, HttpServletRequest request) {
        // find account first
        List<Account> opt = accountService.findByAccountId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Account toDelete = opt.getFirst();

        // get name of currently authenticated principal (usually username/email)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = (auth != null) ? auth.getName() : null;

        // perform the deletion (service method should handle cascade / business rules)
        accountService.deleteByAccountId(id);

        /*
         * If the deleted account matches the currently authenticated account,
         * clear the security context and invalidate the session (user intentionally deleted themselves).
         * Otherwise, do not touch session/auth — prevent accidental sign-outs for admins deleting other accounts.
         */
        try {
            String deletedAccountIdentifier = null;

            // Try to compare on a common identifier field. Adjust field if your Account uses a different name.
            // Example uses notificationEmail (your frontend used "notificationEmail" earlier).
            if (toDelete.getNotificationEmail() != null) {
                deletedAccountIdentifier = toDelete.getNotificationEmail();
            } else if (toDelete.getFullName() != null) {
                deletedAccountIdentifier = toDelete.getFullName();
            } else if (toDelete.getAccountId() != null) {
                deletedAccountIdentifier = String.valueOf(toDelete.getAccountId());
            }

            if (currentPrincipalName != null && currentPrincipalName.equalsIgnoreCase(deletedAccountIdentifier)) {

                // Self-delete: invalidate
                SecurityContextHolder.clearContext();
                HttpSession session = request.getSession(false);
                if (session != null) session.invalidate();

                // optional: call request.logout() if container-managed auth is used
                try { request.logout(); } catch (Exception ignored) {}

                // return no content — client should be redirected to login if necessary
                return ResponseEntity.noContent().build();
            }
        } catch (Exception ex) {
            // if something goes wrong while checking identity, do not invalidate other sessions.
            // log if you have a logger; swallow the exception so deletion isn't rolled back here.
            ex.printStackTrace();
        }

        // Normal deletion of other account — keep current session active
        return ResponseEntity.noContent().build();
    }

    // Find account by library card number
    @GetMapping("/find/card_number/{card_number}")
    public ResponseEntity<?> findByCardNumber(@PathVariable Integer card_number)
    {
        Optional<Account> opt = accountService.findByLibraryCard(card_number);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found for this card number");
        return ResponseEntity.ok(opt.get());
    }

    // Other search endpoints
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

    // Get current logged-in account (for edit page)
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentAccount(Authentication authentication)
    {
        if (authentication == null || !authentication.isAuthenticated())
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        Object principal = authentication.getPrincipal();
        Account account = null;

        if (principal instanceof AccountUserDetails)
        {
            account = ((AccountUserDetails) principal).account();
        }
        else
        {
            // fallback: search by username/email
            List<Account> matches = accountService.findByNotificationEmailIgnoreCase(authentication.getName());
            if (matches != null && !matches.isEmpty()) account = matches.getFirst();
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
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials)
    {
        String email = credentials.get("notificationEmail");
        String password = credentials.get("password");

        if (email == null || password == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and password must be provided.");

        List<Account> accounts = accountService.findByNotificationEmailIgnoreCase(email.trim());
        if (accounts.isEmpty() || !passwordEncoder.matches(password, accounts.getFirst().getPasswordHash()))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        Account account = accounts.getFirst();
        Map<String, Object> response = new HashMap<>();
        response.put("accountId", account.getAccountId());
        response.put("fullName", account.getFullName());
        response.put("email", account.getNotificationEmail());
        response.put("accountType", account.getAccountType());
        return ResponseEntity.ok(response);
    }

    // === Registration endpoint ===
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData)
    {
        try
        {
            String fullName = userData.get("fullName");
            String email = userData.get("email");
            String password = userData.get("password");
            //String confirmPassword = userData.get("confirmPassword");
            String phone = userData.get("phone");
            String libraryCardStr = userData.get("libraryCard");

            // already verified from front-end
            /*
            if (fullName == null || email == null || password == null || confirmPassword == null ||
                    phone == null || libraryCardStr == null)
            {
                return ResponseEntity.badRequest().body("All fields are required.");
            }

            if (!password.equals(confirmPassword))
            {
                return ResponseEntity.badRequest().body("Passwords do not match.");
            }

             */

            if(!password.matches(passwordPolicy)) return ResponseEntity.badRequest().body("Password must contain at least 8 characters with at least 1 letter and 1 number");

            Integer libraryCardId;
            try
            {
                libraryCardId = Integer.parseInt(libraryCardStr);
            }
            catch (NumberFormatException e)
            {
                return ResponseEntity.badRequest().body("Library Card ID must be a number.");
            }

            // check duplicate email
            if (!accountService.findByNotificationEmailIgnoreCase(email).isEmpty())
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered.");
            }

            // find card
            List<LibraryCard> cards = libraryCardService.findByCardNumber(libraryCardId);
            if (cards == null || cards.isEmpty())
            {
                return ResponseEntity.badRequest().body("Library card not found.");
            }

            LibraryCard card = cards.getFirst();
            if (card.getAccount() != null)
            {
                return ResponseEntity.badRequest().body("Library card is already linked to an account.");
            }

            // create and save account
            Account account = new Account();
            account.setFullName(fullName);
            account.setNotificationEmail(email);
            account.setPasswordHash(password);
            account.setPhoneNumber(phone);
            account.setAccountType("MEMBER");

            Account savedAccount = accountService.save(account);

            // link card to saved account and save card
            card.setAccount(savedAccount);
            libraryCardService.save(card);

            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }

    // get recommendations for books
    @GetMapping({"/book_rec/{account_id}"})
    public ResponseEntity<?> findBookRecById(@PathVariable Integer account_id)
    {
        try {
            List<Borrow> borrows = borrowService.findByAccountIdAccount(account_id);
            List<PublicationDTO> recBooks;
            String resHeader; // for determining if there is a book used for recommendation or random suggestions

            if (!borrows.isEmpty()) { //has borrow records
                List<Publication> borrowedBooks = new ArrayList<>(List.of());

                for (Borrow borrow : borrows) {
                    Optional<BookCopy> bc = bookCopyService.findBySerialBarcode(borrow.getSerialBarcodeBookCopy());
                    bc.ifPresent(bookCopy -> borrowedBooks.add(bookCopy.getPublication()));
                }

                // select a random book from borrowed books
                Publication book = borrowedBooks.get(new Random().nextInt(borrowedBooks.size()));

                // get recommendations from same genre of the chosen book borrowed (limit 5)
                List<PublicationDTO> b = publicationService.recBookByGenre(book.getGenre(), book.getIsbn13());
                Collections.shuffle(b); //shuffle all results to get random ones
                recBooks = b.stream().limit(5).toList(); //limit 5
                resHeader = book.getTitle(); // pass book title as header to use for displaying
            } else { // no borrow records
                // get a list of all genres and randomly suggest a book from a random genre
                List<String> genres = publicationService.getGenres();
                List<PublicationDTO> b = publicationService.findByGenre(genres.get(new Random().nextInt(genres.size())));
                Collections.shuffle(b);
                recBooks = b.stream().limit(5).toList();
                resHeader = "random";
            }

            return ResponseEntity.ok()
                    .header("Rec-Type",resHeader)
                    .body(recBooks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred.\n" + e.getMessage());
        }
    }

    @GetMapping({"/borrowed_rec"})
    public ResponseEntity<?> getCurrentlyBorrowedRecord(Authentication authentication)
    {
        // get account from email
        List<Account> a = accountService.findByNotificationEmailIgnoreCase(authentication.getName());
        if (a == null || a.isEmpty()) return ResponseEntity.badRequest().body("Account not found or not logged in.");

        // get borrow records from accountId
        List<Borrow> borrows = borrowService.findByAccountIdAccount(a.getFirst().getAccountId());
        if (borrows == null || borrows.isEmpty())
            return ResponseEntity.badRequest().body("No borrow records found."); // if no borrows return empty

        // get book copy from borrow records
        List<Map<String, String>> result = new ArrayList<>();

        for (Borrow borrow : borrows) {
            if(!borrow.getStatus().equals("Borrowed")) continue;
            // get book from borrow record
            Optional<BookCopy> bc = bookCopyService.findBySerialBarcode(borrow.getSerialBarcodeBookCopy());
            bc.ifPresent(bookCopy -> {
                // check for any fine
                List<Fine> fine = fineService.findByBorrowIdBorrow(borrow.getBorrowId());
                String amount;

                if (fine == null || fine.isEmpty()) {
                    amount = "0";
                } else amount = fine.getFirst().getFineAmount().toString();

                // add to result
                Map<String, String> map = new HashMap<>();
                map.put("borrowId", borrow.getBorrowId().toString());
                map.put("serialBarcode", bookCopy.getSerialBarcode().toString());
                map.put("bookTitle", bookCopy.getPublication().getTitle());
                map.put("borrowDate", borrow.getBorrowedDate().toString());
                map.put("dueDate", borrow.getDueDate().toString());
                map.put("amount", amount);

                result.add(map);
            });
        }

        return ResponseEntity.ok().body(result);
    }
}