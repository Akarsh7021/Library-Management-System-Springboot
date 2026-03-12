package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Hold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface HoldRepository extends JpaRepository<Hold, Integer>
{

    List<Hold> findByBookHoldId(Integer bookHoldId);

    List<Hold> findByAccountIdAccount(Integer accountId);

    List<Hold> findBySerialBarcodeBookCopy(Integer serialBarcodeBookCopy);

    Optional<Hold> findFirstBySerialBarcodeBookCopyAndAccountIdAccount(Integer serialBarcodeBookCopy, Integer accountId);

    List<Hold> findByHoldExpiryBefore(LocalDate holdExpiryBefore);

    List<Hold> findByHoldExpiryAfter(LocalDate holdExpiryAfter);
}
