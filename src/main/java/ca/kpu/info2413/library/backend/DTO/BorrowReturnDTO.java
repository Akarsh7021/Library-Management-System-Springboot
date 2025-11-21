package ca.kpu.info2413.library.backend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class BorrowReturnDTO {
    // getters / setters
    private Integer accountId;
    private String fullName;
    private String bookTitle;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private BigDecimal fine;

    public BorrowReturnDTO() {}

    public BorrowReturnDTO(Integer accountId, String fullName, String bookTitle,
                           LocalDate borrowedDate, LocalDate dueDate, LocalDate returnedDate, BigDecimal fine) {
        this.accountId = accountId;
        this.fullName = fullName;
        this.bookTitle = bookTitle;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
        this.fine = fine;
    }

}

