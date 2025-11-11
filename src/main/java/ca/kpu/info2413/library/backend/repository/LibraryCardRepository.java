package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface LibraryCardRepository extends JpaRepository<LibraryCard, Integer>
{

    List<LibraryCard> findByCardNumber(Integer cardNumber);
}
