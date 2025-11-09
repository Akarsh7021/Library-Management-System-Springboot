package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface BorrowRepository extends JpaRepository<Borrow, Integer>
{

    List<Borrow> findByBorrowId(Integer borrowId);

}
