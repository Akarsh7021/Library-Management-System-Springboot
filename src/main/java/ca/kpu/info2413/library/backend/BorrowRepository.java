package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

    List<Borrow> findByBorrowId(Integer borrowId);
    List<Borrow> findByStatus(String status);
    List<Borrow> findByAccountIdAccount(Integer accountIdAccount);
    List<Borrow> findByDueDateBefore(java.time.LocalDate date);

    // Advanced query: Find overdue borrows with account details
    @Query("SELECT b FROM Borrow b JOIN b.account a WHERE b.dueDate < CURRENT_DATE AND b.status = 'active'")
    List<Borrow> findOverdueBorrowsWithAccountDetails();

}
