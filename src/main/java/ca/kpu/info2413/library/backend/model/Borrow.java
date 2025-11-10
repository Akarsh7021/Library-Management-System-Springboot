package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Borrow")
@Data
@NoArgsConstructor
public class Borrow
{
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
}
