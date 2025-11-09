package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BookCopyConditionNotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "condition_notes")
    private String conditionNotes;

    @Column(name = "serial_barcode_BookCopy")
    private Integer serialBarcodeBookCopy;
}