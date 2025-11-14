package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BookCopyConditionNotes")
@Data
@NoArgsConstructor
public class BookCopyConditionNotes
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serial_barcode_BookCopy")
    private Integer serialBarcodeBookCopy;

    @Column(name = "condition_notes")
    private String conditionNotes;


    public static void deleteBySerialBarcodeBookCopy(Integer serialBarcodeBookCopy)
    {
    }
}