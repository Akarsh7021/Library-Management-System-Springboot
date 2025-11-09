package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Hold
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_hold_id")
    private Integer bookHoldId;

    @Column(name = "hold_expiry")
    private LocalDate holdExpiry;

    @Column(name = "held_since")
    private LocalDate heldSince;

    @Column(name = "serial_barcode_BookCopy")
    private Integer serialBarcodeBookCopy;

    @Column(name = "account_id_Account")
    private Integer accountIdAccount;
}
