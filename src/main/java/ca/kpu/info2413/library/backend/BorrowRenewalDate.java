package ca.kpu.info2413.library.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class BorrowRenewalDate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private java.sql.Date renewal_date;
    private int borrow_id_Borrow;


    // for JPA only, no use
    public BorrowRenewalDate() {
    }

    // getters, setters and constructor
}