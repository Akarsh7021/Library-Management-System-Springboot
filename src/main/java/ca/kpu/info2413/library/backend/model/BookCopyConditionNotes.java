package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "BookCopyConditionNotes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyConditionNotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Integer noteId;

    @Column(name = "serial_barcode_BookCopy", nullable = false)
    private Integer serialBarcodeBookCopy;

    @Column(name = "condition_notes", nullable = false, columnDefinition = "text")
    private String conditionNotes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
