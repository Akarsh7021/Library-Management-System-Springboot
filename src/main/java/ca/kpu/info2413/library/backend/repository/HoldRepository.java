package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Hold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface HoldRepository extends JpaRepository<Hold, Integer>
{

    List<Hold> findByBookHoldId(Integer bookHoldId);

}
