package ca.kpu.info2413.library.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int fine_id;
    private int fine_amount;
    private java.sql.Date issue_date;
    private boolean waived_reversed;
    private int borrow_id_Borrow;

    // for JPA only, no use
    public Fine() {
    }

    // getters, setters and constructor
}
