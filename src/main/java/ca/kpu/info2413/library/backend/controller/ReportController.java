package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController
{

    private final ReportService reportService;

    public ReportController(ReportService reportService)
    {
        this.reportService = reportService;
    }

    /**
     * Dashboard aggregate endpoint used by LibraryReportsDashboard.html
     * Accepts JSON body: { "startDate": "YYYY-MM-DD", "endDate": "YYYY-MM-DD" }
     * Both fields are optional (null/empty => all-time).
     */
    @PostMapping("/dashboard")
    public Map<String, Object> generateDashboardReport(@RequestBody Map<String, String> body)
    {
        String sStart = body.get("startDate");
        String sEnd = body.get("endDate");

        LocalDate start = (sStart == null || sStart.isBlank()) ? null : LocalDate.parse(sStart);
        LocalDate end = (sEnd == null || sEnd.isBlank()) ? null : LocalDate.parse(sEnd);

        return reportService.getDashboard(start, end);
    }
}
