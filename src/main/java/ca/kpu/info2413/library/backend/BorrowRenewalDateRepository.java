package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BorrowRenewalDateRepository extends JpaRepository<BorrowRenewalDate, java.sql.Date> {

    @Query("SELECT brd FROM BorrowRenewalDate brd WHERE brd.renewal_date = :renewal_date")
    List<BorrowRenewalDate> findByRenewal_date(@Param("renewal_date") java.sql.Date renewal_date);

    @Query("SELECT brd FROM BorrowRenewalDate brd WHERE brd.borrow_id_Borrow = :borrow_id_Borrow")
    List<BorrowRenewalDate> findByBorrow_id_Borrow(@Param("borrow_id_Borrow") int borrow_id_Borrow);

}