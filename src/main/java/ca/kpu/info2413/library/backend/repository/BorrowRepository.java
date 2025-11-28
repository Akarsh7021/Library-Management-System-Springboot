package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BorrowRepository extends JpaRepository<Borrow, Integer>
{

    List<Borrow> findByBorrowId(Integer borrowId);

    void deleteByBorrowId(Integer borrowId);

    List<Borrow> findBySerialBarcodeBookCopy(Integer serialBarcodeBookCopy);

    List<Borrow> findByAccountIdAccount(Integer accountIdAccount);

    List<Borrow> findByDueDateBeforeAndStatus(LocalDate dueDate, String status);
}
