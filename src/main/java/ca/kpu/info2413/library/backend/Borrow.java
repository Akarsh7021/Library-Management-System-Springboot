package ca.kpu.info2413.library.backend;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int borrow_id;
    private String status;
    private java.sql.Date borrowed_date;
    private java.sql.Date returned_date;
    private java.sql.Date due_date;
    private int serial_barcode_BookCopy;
    private int account_id_Account;

    // for JPA only, no use
    public Borrow() {
    }

    // getters, setters and constructor
}
