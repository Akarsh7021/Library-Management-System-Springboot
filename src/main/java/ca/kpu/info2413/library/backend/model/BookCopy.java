package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class BookCopy
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "serial_barcode")
    private Integer serialBarcode;

    @Column(name = "date_acquired")
    private LocalDate dateAcquired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isbn_13_Publication", nullable = false)
    private Publication publication;
}

