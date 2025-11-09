package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fine_id")
    private Integer fineId;

    @Column(name = "fine_amount")
    private Integer fineAmount;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "waived_reversed")
    private boolean waivedReversed;

    @Column(name = "borrow_id_Borrow")
    private Integer borrowIdBorrow;
}
