package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class BorrowRenewalDate
{
    @Id
    @Column(name = "renewal_date")
    private LocalDate renewalDate;

    @Column(name = "borrow_id_Borrow")
    private Integer borrowIdBorrow;
}