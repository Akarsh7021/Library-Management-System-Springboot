package ca.kpu.info2413.library.backend.repository;

import ca.kpu.info2413.library.backend.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Borrow, Integer> {

    // -----------------------------------------------------
    // 1. Borrow / Return Report (Filtered)
    // -----------------------------------------------------
    @Query(value =
            "SELECT a.account_id, a.full_name, p.title, b.borrowed_date, b.due_date, b.returned_date, " +
                    "       COALESCE(f.fine_amount, 0) AS fine " +
                    "FROM \"Borrow\" b " +
                    "JOIN \"Account\" a ON a.account_id = b.\"account_id_Account\" " +
                    "JOIN \"BookCopy\" bc ON bc.serial_barcode = b.\"serial_barcode_BookCopy\" " +
                    "JOIN \"Publication\" p ON p.isbn_13 = bc.\"isbn_13_Publication\" " +
                    "LEFT JOIN \"Fine\" f ON f.\"borrow_id_Borrow\" = b.borrow_id " +
                    "WHERE (:userId IS NULL OR a.account_id = :userId) " +
                    "  AND (:fullName IS NULL OR a.full_name ILIKE CONCAT('%', :fullName, '%')) " +
                    "  AND (:bookTitle IS NULL OR p.title ILIKE CONCAT('%', :bookTitle, '%')) " +
                    "  AND (CAST(:start AS DATE) IS NULL OR b.borrowed_date >= CAST(:start AS DATE)) " +  //FIXED
                    "  AND (CAST(:end   AS DATE) IS NULL OR b.borrowed_date <= CAST(:end   AS DATE))\n " +     //FIXED
                    "ORDER BY b.borrowed_date DESC",
            nativeQuery = true)
    List<Object[]> rawBorrowReturnReport(
            @Param("userId") Integer userId,
            @Param("fullName") String fullName,
            @Param("bookTitle") String bookTitle,
            @Param("start") java.time.LocalDate start,
            @Param("end") java.time.LocalDate end
    );

    // -----------------------------------------------------
    // 2. Fine Summary
    // -----------------------------------------------------
    @Query(value =
            "SELECT a.account_id, a.full_name, COALESCE(SUM(f.fine_amount),0) AS total_fines, MAX(f.issue_date) AS last_fine_date " +
                    "FROM \"Fine\" f " +
                    "JOIN \"Borrow\" b ON b.borrow_id = f.\"borrow_id_Borrow\" " +
                    "JOIN \"Account\" a ON a.account_id = b.\"account_id_Account\" " +
                    "GROUP BY a.account_id, a.full_name " +
                    "ORDER BY total_fines DESC",
            nativeQuery = true)
    List<Object[]> rawFineSummary();


    // -----------------------------------------------------
    // 3. Genre Trend (no-arg, all-time)
    // -----------------------------------------------------
    @Query(value =
            "SELECT p.genre, COUNT(*) AS borrow_count " +
                    "FROM \"Borrow\" b " +
                    "JOIN \"BookCopy\" bc ON bc.serial_barcode = b.\"serial_barcode_BookCopy\" " +
                    "JOIN \"Publication\" p ON p.isbn_13 = bc.\"isbn_13_Publication\" " +
                    "GROUP BY p.genre " +
                    "ORDER BY borrow_count DESC",
            nativeQuery = true)
    List<Object[]> rawGenreTrend(); // all-time

    // -----------------------------------------------------
    // 3b. Genre Trend (with date range)
    // -----------------------------------------------------
    @Query(value =
            "SELECT p.genre, COUNT(*) AS borrow_count " +
                    "FROM \"Borrow\" b " +
                    "JOIN \"BookCopy\" bc ON bc.serial_barcode = b.\"serial_barcode_BookCopy\" " +
                    "JOIN \"Publication\" p ON p.isbn_13 = bc.\"isbn_13_Publication\" " +
                    "WHERE (CAST(:start AS DATE) IS NULL OR b.borrowed_date >= CAST(:start AS DATE))" +
                    "AND   (CAST(:end   AS DATE) IS NULL OR b.borrowed_date <= CAST(:end   AS DATE)) " +
                    "GROUP BY p.genre " +
                    "ORDER BY borrow_count DESC",
            nativeQuery = true)
    List<Object[]> rawGenreTrend(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );


    // -----------------------------------------------------
    // 4. Author Trend (no-arg, all-time)
    // -----------------------------------------------------
    @Query(value =
            "SELECT au.author_name, COUNT(*) AS borrow_count " +
                    "FROM \"Borrow\" b " +
                    "JOIN \"BookCopy\" bc ON bc.serial_barcode = b.\"serial_barcode_BookCopy\" " +
                    "JOIN \"Publication\" p ON p.isbn_13 = bc.\"isbn_13_Publication\" " +
                    "JOIN \"PublicationAuthor\" pa ON pa.\"isbn_13_Publication\" = p.isbn_13 " +
                    "JOIN \"Author\" au ON au.author_id = pa.\"author_id_Author\" " +
                    "GROUP BY au.author_name " +
                    "ORDER BY borrow_count DESC",
            nativeQuery = true)
    List<Object[]> rawAuthorTrend(); // all-time

    // -----------------------------------------------------
    // 4b. Author Trend (with date range)
    // -----------------------------------------------------
    @Query(value =
            "SELECT au.author_name, COUNT(*) AS borrow_count " +
                    "FROM \"Borrow\" b " +
                    "JOIN \"BookCopy\" bc ON bc.serial_barcode = b.\"serial_barcode_BookCopy\" " +
                    "JOIN \"Publication\" p ON p.isbn_13 = bc.\"isbn_13_Publication\" " +
                    "JOIN \"PublicationAuthor\" pa ON pa.\"isbn_13_Publication\" = p.isbn_13 " +
                    "JOIN \"Author\" au ON au.author_id = pa.\"author_id_Author\" " +
                    "WHERE (CAST(:start AS DATE) IS NULL OR b.borrowed_date >= CAST(:start AS DATE))" +
                    "AND   (CAST(:end   AS DATE) IS NULL OR b.borrowed_date <= CAST(:end   AS DATE)) " +
                    "GROUP BY au.author_name " +
                    "ORDER BY borrow_count DESC",
            nativeQuery = true)
    List<Object[]> rawAuthorTrend(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );


    // -----------------------------------------------------
    // 5. Monthly Trend (no-arg, all-time)
    // -----------------------------------------------------
    @Query(value =
            "SELECT TO_CHAR(b.borrowed_date, 'YYYY-MM') AS month, COUNT(*) AS borrow_count " +
                    "FROM \"Borrow\" b " +
                    "WHERE b.borrowed_date IS NOT NULL " +
                    "GROUP BY TO_CHAR(b.borrowed_date, 'YYYY-MM') " +
                    "ORDER BY TO_CHAR(b.borrowed_date, 'YYYY-MM')",
            nativeQuery = true)
    List<Object[]> rawMonthlyTrend(); // all-time

    // -----------------------------------------------------
    // 5b. Monthly Trend (date range)
    // -----------------------------------------------------
    @Query(value =
            "SELECT TO_CHAR(b.borrowed_date, 'YYYY-MM') AS month, COUNT(*) AS borrow_count " +
                    "FROM \"Borrow\" b " +
                    "WHERE b.borrowed_date IS NOT NULL " +
                    "  AND (:start IS NULL OR b.borrowed_date >= :start) " +
                    "  AND (:end IS NULL OR b.borrowed_date <= :end) " +
                    "GROUP BY TO_CHAR(b.borrowed_date, 'YYYY-MM') " +
                    "ORDER BY TO_CHAR(b.borrowed_date, 'YYYY-MM')",
            nativeQuery = true)
    List<Object[]> rawMonthlyTrend(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );


    // -----------------------------------------------------
    // 6. Top Publications
    // -----------------------------------------------------
    @Query(value =
            "SELECT p.isbn_13, p.title, COUNT(*) AS borrow_count " +
                    "FROM \"Borrow\" b " +
                    "JOIN \"BookCopy\" bc ON bc.serial_barcode = b.\"serial_barcode_BookCopy\" " +
                    "JOIN \"Publication\" p ON p.isbn_13 = bc.\"isbn_13_Publication\" " +
                    "GROUP BY p.isbn_13, p.title " +
                    "ORDER BY borrow_count DESC " +
                    "LIMIT :limit",
            nativeQuery = true)
    List<Object[]> rawTopPublications(@Param("limit") int limit);

    // -----------------------------------------------------
    // 7. Inventory Counts
    // -----------------------------------------------------
    @Query(value =
            "SELECT p.isbn_13, p.title, COUNT(*) AS copy_count " +
                    "FROM \"BookCopy\" bc " +
                    "JOIN \"Publication\" p ON p.isbn_13 = bc.\"isbn_13_Publication\" " +
                    "GROUP BY p.isbn_13, p.title " +
                    "ORDER BY copy_count DESC",
            nativeQuery = true)
    List<Object[]> rawInventory();

    // -----------------------------------------------------
    // 8. Top Borrowers
    // -----------------------------------------------------
    @Query(value =
            "SELECT a.account_id, a.full_name, COUNT(*) AS borrow_count " +
                    "FROM \"Borrow\" b " +
                    "JOIN \"Account\" a ON a.account_id = b.\"account_id_Account\" " +
                    "GROUP BY a.account_id, a.full_name " +
                    "ORDER BY borrow_count DESC " +
                    "LIMIT :limit",
            nativeQuery = true)
    List<Object[]> rawTopBorrowers(@Param("limit") int limit);

    // -----------------------------------------------------
    // 9. Outstanding Fines
    // -----------------------------------------------------
    @Query(value =
            "SELECT a.account_id, a.full_name, COALESCE(SUM(f.fine_amount),0) AS total_fines " +
                    "FROM \"Fine\" f " +
                    "JOIN \"Borrow\" b ON b.borrow_id = f.\"borrow_id_Borrow\" " +
                    "JOIN \"Account\" a ON a.account_id = b.\"account_id_Account\" " +
                    "WHERE (f.waived_reversed = FALSE OR f.waived_reversed IS NULL) " +
                    "GROUP BY a.account_id, a.full_name " +
                    "ORDER BY total_fines DESC",
            nativeQuery = true)
    List<Object[]> rawOutstandingFines();

    // -----------------------------------------------------
    // 10. Payments Summary
    // -----------------------------------------------------
    @Query(value =
            "SELECT a.account_id, a.full_name, COALESCE(SUM(p.payment_amount),0) AS total_paid " +
                    "FROM \"Payment\" p " +
                    "JOIN \"Account\" a ON a.account_id = p.\"account_id_Account\" " +
                    "GROUP BY a.account_id, a.full_name " +
                    "ORDER BY total_paid DESC",
            nativeQuery = true)
    List<Object[]> rawPaymentsSummary();

    // -----------------------------------------------------
    // 11. Active Holds
    // -----------------------------------------------------
    @Query(value =
            "SELECT p.isbn_13, p.title, COUNT(*) AS hold_count " +
                    "FROM \"Hold\" h " +
                    "JOIN \"BookCopy\" bc ON bc.serial_barcode = h.\"serial_barcode_BookCopy\" " +
                    "JOIN \"Publication\" p ON p.isbn_13 = bc.\"isbn_13_Publication\" " +
                    "WHERE h.hold_expiry >= CURRENT_DATE " +
                    "GROUP BY p.isbn_13, p.title " +
                    "ORDER BY hold_count DESC",
            nativeQuery = true)
    List<Object[]> rawActiveHolds();

    // -----------------------------------------------------
    // 12. Expired Holds
    // -----------------------------------------------------
    @Query(value =
            "SELECT h.* " +
                    "FROM \"Hold\" h " +
                    "WHERE h.hold_expiry < CURRENT_DATE",
            nativeQuery = true)
    List<Object[]> rawExpiredHolds();

    // -----------------------------------------------------
    // 13. Overdue Borrows
    // -----------------------------------------------------
    @Query(value =
            "SELECT b.* " +
                    "FROM \"Borrow\" b " +
                    "WHERE b.returned_date IS NULL " +
                    "  AND b.due_date < CURRENT_DATE",
            nativeQuery = true)
    List<Object[]> rawOverdueBorrows();
}
