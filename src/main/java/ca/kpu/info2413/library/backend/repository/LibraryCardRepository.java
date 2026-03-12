package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryCardRepository extends JpaRepository<LibraryCard, Integer>
{

    // find by card number (your column is named card_number)
    List<LibraryCard> findByCardNumber(Integer cardNumber);

    // convenience: find first card by card number
    Optional<LibraryCard> findFirstByCardNumber(Integer cardNumber);

    // find cards by linked account id (property path: account.accountId)
    List<LibraryCard> findByAccountAccountId(Integer accountId);
}