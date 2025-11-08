package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "borrow_id")
    private Integer borrowId;

    private String status;

    @Column(name = "borrowed_date")
    private LocalDate borrowedDate;

    @Column(name = "returned_date")
    private LocalDate returnedDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "serial_barcode_BookCopy")
    private Integer serialBarcodeBookCopy;

    @Column(name = "account_id_Account")
    private Integer accountIdAccount;

    // for JPA only, no use
    public Borrow() {
    }

    // getters, setters and constructor
}
