package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Entity
@IdClass(BookCopyConditionNotes.BookCopyConditionNotesId.class)
public class BookCopyConditionNotes {
    @Id
    @Column(name = "condition_notes")
    private String conditionNotes;

    @Id
    @Column(name = "serial_barcode_BookCopy")
    private Integer serialBarcodeBookCopy;

    @ManyToOne
    @JoinColumn(name = "serial_barcode_BookCopy", insertable = false, updatable = false)
    private BookCopy bookCopy;

    // for JPA only, no use
    public BookCopyConditionNotes() {
    }

    // getters, setters and constructor

    public static class BookCopyConditionNotesId implements Serializable {
        private String conditionNotes;
        private Integer serialBarcodeBookCopy;

        // getters, setters, equals, hashCode
    }
}