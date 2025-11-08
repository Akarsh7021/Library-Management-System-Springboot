package ca.kpu.info2413.library.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findByAccountId(Integer accountId);
    List<Account> findByAccountType(String accountType);
    List<Account> findByNotificationEmail(String notificationEmail);
    List<Account> findByFullName(String fullName);

    // Advanced queries
    @Query("SELECT a FROM Account a WHERE a.accountType = :type AND a.notificationEmail LIKE %:email%")
    List<Account> findByAccountTypeAndNotificationEmailContaining(@Param("type") String accountType, @Param("email") String email);

}