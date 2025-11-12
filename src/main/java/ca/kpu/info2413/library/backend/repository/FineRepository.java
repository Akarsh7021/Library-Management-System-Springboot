package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface FineRepository extends JpaRepository<Fine, Integer>
{

    List<Fine> findByFineId(Integer fineId);

    List<Fine> findByBorrowIdBorrow(Integer borrowIdBorrow);

}