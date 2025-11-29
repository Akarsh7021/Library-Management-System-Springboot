package ca.kpu.info2413.library.backend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class FineSummaryDTO
{
    // getters / setters
    private Integer accountId;
    private String fullName;
    private BigDecimal totalFine;
    private LocalDate lastFineDate;

    public FineSummaryDTO()
    {
    }

    public FineSummaryDTO(Integer accountId, String fullName, BigDecimal totalFine, LocalDate lastFineDate)
    {
        this.accountId = accountId;
        this.fullName = fullName;
        this.totalFine = totalFine;
        this.lastFineDate = lastFineDate;
    }

}
