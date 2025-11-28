package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.DTO.*;
import ca.kpu.info2413.library.backend.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository repo;

    public ReportService(ReportRepository repo) {
        this.repo = repo;
    }

    // Generate filtered borrow & return table
    public List<BorrowReturnDTO> getBorrowReturnReport(Integer userId, String fullName, String bookTitle, LocalDate start, LocalDate end) {
        List<Object[]> rows = repo.rawBorrowReturnReport(
                userId,
                (fullName == null || fullName.isBlank()) ? null : fullName,
                (bookTitle == null || bookTitle.isBlank()) ? null : bookTitle,
                start,
                end
        );
        List<BorrowReturnDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            Integer accountId = r[0] == null ? null : ((Number) r[0]).intValue();
            String name = (String) r[1];
            String title = (String) r[2];
            LocalDate borrowed = r[3] == null ? null : ((Date) r[3]).toLocalDate();
            LocalDate due = r[4] == null ? null : ((Date) r[4]).toLocalDate();
            LocalDate returned = r[5] == null ? null : ((Date) r[5]).toLocalDate();
            BigDecimal fine = r[6] == null ? BigDecimal.ZERO : new BigDecimal(r[6].toString());
            out.add(new BorrowReturnDTO(accountId, name, title, borrowed, due, returned, fine));
        }
        return out;
    }

    // Fine summary
    public List<FineSummaryDTO> getFineSummary() {
        List<Object[]> rows = repo.rawFineSummary();
        List<FineSummaryDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            Integer accountId = r[0] == null ? null : ((Number) r[0]).intValue();
            String name = (String) r[1];
            BigDecimal total = r[2] == null ? BigDecimal.ZERO : new BigDecimal(r[2].toString());
            LocalDate last = r[3] == null ? null : ((Date) r[3]).toLocalDate();
            out.add(new FineSummaryDTO(accountId, name, total, last));
        }
        return out;
    }

    // -------------------------
    // Genre (all-time)
    // -------------------------
    public List<GenreTrendDTO> getGenreTrend() {
        List<Object[]> rows = repo.rawGenreTrend();
        List<GenreTrendDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            String genre = (String) r[0];
            Long cnt = r[1] == null ? 0L : ((Number) r[1]).longValue();
            out.add(new GenreTrendDTO(genre, cnt));
        }
        return out;
    }

    // -------------------------
    // Genre (date range)
    // -------------------------
    public List<GenreTrendDTO> getGenreTrends(LocalDate start, LocalDate end) {
        List<Object[]> rows = repo.rawGenreTrend(start, end);
        List<GenreTrendDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            String genre = (String) r[0];
            Long count = r[1] == null ? 0L : ((Number) r[1]).longValue();
            out.add(new GenreTrendDTO(genre, count));
        }
        return out;
    }

    // -------------------------
    // Monthly (all-time)
    // -------------------------
    public List<MonthlyTrendDTO> getMonthlyTrend() {
        List<Object[]> rows = repo.rawMonthlyTrend();
        List<MonthlyTrendDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            String period = (String) r[0];
            Long cnt = r[1] == null ? 0L : ((Number) r[1]).longValue();
            out.add(new MonthlyTrendDTO(period, cnt));
        }
        return out;
    }

    // -------------------------
    // Monthly (date-range)
    // -------------------------
    public List<MonthlyTrendDTO> getBorrowingTrends(LocalDate start, LocalDate end) {
        List<Object[]> rows = repo.rawMonthlyTrend(start, end);
        List<MonthlyTrendDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            String period = (String) r[0];
            Long count = r[1] == null ? 0L : ((Number) r[1]).longValue();
            out.add(new MonthlyTrendDTO(period, count));
        }
        return out;
    }

    // -------------------------
    // Author (all-time)
    // -------------------------
    public List<AuthorTrendDTO> getAuthorTrend() {
        List<Object[]> rows = repo.rawAuthorTrend();
        List<AuthorTrendDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            String author = (String) r[0];
            Long cnt = r[1] == null ? 0L : ((Number) r[1]).longValue();
            out.add(new AuthorTrendDTO(author, cnt));
        }
        return out;
    }

    // -------------------------
    // Author (date-range)
    // -------------------------
    public List<AuthorTrendDTO> getAuthorTrends(LocalDate start, LocalDate end) {
        List<Object[]> rows = repo.rawAuthorTrend(start, end);
        List<AuthorTrendDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            String author = (String) r[0];
            Long count = r[1] == null ? 0L : ((Number) r[1]).longValue();
            out.add(new AuthorTrendDTO(author, count));
        }
        return out;
    }

    // -------------------------
    // the rest (publications, inventory, etc.)
    // -------------------------
    public List<PublicationReportDTO> getTopPublications(int limit) {
        List<Object[]> rows = repo.rawTopPublications(limit);
        List<PublicationReportDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            Long isbn = r[0] == null ? null : ((Number) r[0]).longValue();
            String title = (String) r[1];
            Long cnt = r[2] == null ? 0L : ((Number) r[2]).longValue();
            out.add(new PublicationReportDTO(isbn, title, null, cnt));
        }
        return out;
    }

    public List<PublicationReportDTO> getInventory() {
        List<Object[]> rows = repo.rawInventory();
        List<PublicationReportDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            Long isbn = r[0] == null ? null : ((Number) r[0]).longValue();
            String title = (String) r[1];
            Long cnt = r[2] == null ? 0L : ((Number) r[2]).longValue();
            out.add(new PublicationReportDTO(isbn, title, null, cnt));
        }
        return out;
    }

    public List<PublicationReportDTO> getTopBorrowers(int limit) {
        List<Object[]> rows = repo.rawTopBorrowers(limit);
        List<PublicationReportDTO> out = new ArrayList<>();
        // Map as PublicationReportDTO temporarily: isbn -> accountId stored in isbn13 field; title -> name; borrowCount -> count
        for (Object[] r : rows) {
            Long accountId = r[0] == null ? null : ((Number) r[0]).longValue();
            String name = (String) r[1];
            Long cnt = r[2] == null ? 0L : ((Number) r[2]).longValue();
            out.add(new PublicationReportDTO(accountId, name, null, cnt));
        }
        return out;
    }

    public List<PublicationReportDTO> getOutstandingFines() {
        List<Object[]> rows = repo.rawOutstandingFines();
        List<PublicationReportDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            Long accountId = r[0] == null ? null : ((Number) r[0]).longValue();
            String name = (String) r[1];
            Long total = r[2] == null ? 0L : ((Number) r[2]).longValue();
            out.add(new PublicationReportDTO(accountId, name, null, total));
        }
        return out;
    }

    public List<PublicationReportDTO> getPaymentsSummary() {
        List<Object[]> rows = repo.rawPaymentsSummary();
        List<PublicationReportDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            Long accountId = r[0] == null ? null : ((Number) r[0]).longValue();
            String name = (String) r[1];
            Long total = r[2] == null ? 0L : ((Number) r[2]).longValue();
            out.add(new PublicationReportDTO(accountId, name, null, total));
        }
        return out;
    }

    public List<PublicationReportDTO> getActiveHolds() {
        List<Object[]> rows = repo.rawActiveHolds();
        List<PublicationReportDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            Long isbn = r[0] == null ? null : ((Number) r[0]).longValue();
            String title = (String) r[1];
            Long cnt = r[2] == null ? 0L : ((Number) r[2]).longValue();
            out.add(new PublicationReportDTO(isbn, title, null, cnt));
        }
        return out;
    }

    public List<Object[]> getExpiredHolds() {
        return repo.rawExpiredHolds();
    }

    public List<Object[]> getOverdueBorrows() {
        return repo.rawOverdueBorrows();
    }
    // -------------------------
// DASHBOARD AGGREGATED REPORT
// -------------------------
    public Map<String, Object> getDashboard(LocalDate start, LocalDate end) {
        Map<String, Object> out = new java.util.HashMap<>();

        // 1. Borrow & Return Table (filters by date automatically)
        List<BorrowReturnDTO> borrowRows = getBorrowReturnReport(
                null,   // userId filter
                null,   // fullName filter
                null,   // bookTitle filter
                start,
                end
        );
        out.put("borrowReturn", borrowRows);

        // 2. Fine Summary (currently all-time - can filter later if you want)
        List<FineSummaryDTO> fine = getFineSummary();
        out.put("fineSummary", fine);

        // 3. Genre Trends
        List<GenreTrendDTO> genres = getGenreTrends(start, end);
        List<Map<String, Object>> genreStats = new ArrayList<>();
        for (GenreTrendDTO g : genres) {
            Map<String, Object> m = new HashMap<>();
            m.put("genre", g.getGenre());
            m.put("count", g.getBorrowCount());
            genreStats.add(m);
        }
        out.put("genreStats", genreStats);

        // 4. Author Trends
        List<AuthorTrendDTO> authors = getAuthorTrends(start, end);
        List<Map<String, Object>> authorStats = new ArrayList<>();
        for (AuthorTrendDTO a : authors) {
            Map<String, Object> m = new HashMap<>();
            m.put("author", a.getAuthorName());
            m.put("count", a.getBorrowCount());
            authorStats.add(m);
        }
        out.put("authorStats", authorStats);

        // 5. Borrowing Trends (monthly)
        List<MonthlyTrendDTO> months = getBorrowingTrends(start, end);
        List<Map<String, Object>> monthly = new ArrayList<>();
        for (MonthlyTrendDTO mt : months) {
            Map<String, Object> m = new HashMap<>();
            m.put("month", mt.getPeriod());
            m.put("count", mt.getBorrowCount());
            monthly.add(m);
        }
        out.put("borrowTrend", monthly);

        return out;
    }

}
