package ca.kpu.info2413.library.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthlyTrendDTO
{
    private String period; // "2025-04"
    private Long borrowCount;

    public MonthlyTrendDTO()
    {
    }

    public MonthlyTrendDTO(String period, Long borrowCount)
    {
        this.period = period;
        this.borrowCount = borrowCount;
    }

}
