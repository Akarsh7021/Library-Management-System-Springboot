package ca.kpu.info2413.library.backend;

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

    @Column(name = "isbn_13_Publication")
    private Integer isbn13Publication;
}

