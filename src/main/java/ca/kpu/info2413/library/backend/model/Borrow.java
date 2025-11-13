package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Borrow") // use lowercase table name for PostgreSQL
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_id")
    private Integer borrowId;

    @Column(name = "status")
    private String status = "Borrowed"; // default when created

    @Column(name = "borrowed_date")
    private LocalDate borrowedDate = LocalDate.now();

    @Column(name = "returned_date")
    private LocalDate returnedDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "serial_barcode_BookCopy")
    private Integer serialBarcodeBookCopy;

    @Column(name = "account_id_Account")
    private Integer accountIdAccount;
}