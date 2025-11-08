package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface LibraryCardRepository extends JpaRepository<LibraryCard, Integer> {

    @Query("SELECT l FROM LibraryCard l WHERE l.card_number = :card_number")
    List<LibraryCard> findByCard_number(@Param("card_number") int card_number);

}
