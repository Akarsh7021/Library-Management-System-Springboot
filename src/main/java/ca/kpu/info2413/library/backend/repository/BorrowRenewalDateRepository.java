package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.BorrowRenewalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BorrowRenewalDateRepository extends JpaRepository<BorrowRenewalDate, LocalDate>
{

    List<BorrowRenewalDate> findByRenewalDate(LocalDate renewalDate);

    List<BorrowRenewalDate> findByBorrowIdBorrow(Integer borrowIdBorrow);

    void deleteByBorrowIdBorrow(Integer borrowIdBorrow);
}