package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class BorrowRenewalDate {
    @Id
    @Column(name = "renewal_date")
    private LocalDate renewalDate;

    @Column(name = "borrow_id_Borrow")
    private Integer borrowIdBorrow;

    @ManyToOne
    @JoinColumn(name = "borrow_id_Borrow", insertable = false, updatable = false)
    private Borrow borrow;

    // for JPA only, no use
    public BorrowRenewalDate() {
    }

    // getters, setters and constructor
}