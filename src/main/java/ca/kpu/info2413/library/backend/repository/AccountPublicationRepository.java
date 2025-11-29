package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.AccountPublication;
import ca.kpu.info2413.library.backend.model.AccountPublicationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountPublicationRepository extends JpaRepository<AccountPublication, AccountPublicationId>
{
    // All waitlist entries for a publication ordered by waitlistPosition
    @Query("SELECT ap FROM AccountPublication ap WHERE ap.publication.isbn13 = :isbn ORDER BY ap.waitlistPosition")
    List<AccountPublication> findByPublicationIsbn13OrderByPosition(@Param("isbn") Long isbn);

    // All waitlist entries for a specific account
    List<AccountPublication> findByAccountAccountId(Integer accountId);

    // find existing pair
    @Query("SELECT ap FROM AccountPublication ap WHERE ap.publication.isbn13 = :isbn AND ap.account.accountId = :accountId")
    Optional<AccountPublication> findByIsbnAndAccount(@Param("isbn") Long isbn, @Param("accountId") Integer accountId);

    // max waitlist position current for a publication (can be null)
    @Query("SELECT MAX(ap.waitlistPosition) FROM AccountPublication ap WHERE ap.publication.isbn13 = :isbn")
    Short findMaxWaitlistPositionForIsbn(@Param("isbn") Long isbn);
}
