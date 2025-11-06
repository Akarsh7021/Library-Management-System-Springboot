package ca.kpu.info2413.library.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int payment_id;
    private java.sql.Date payment_date;
    private String payment_method;
    private String processed_by;
    private int account_id_Account;

    // for JPA only, no use
    public Payment() {
    }

    // getters, setters and constructor
}
