package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BookCopyConditionNotes
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "condition_notes")
    private String conditionNotes;

    @Column(name = "serial_barcode_BookCopy")
    private Integer serialBarcodeBookCopy;
}