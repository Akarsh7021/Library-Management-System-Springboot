package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "serial_barcode")
    private Integer serialBarcode;

    @Column(name = "date_acquired")
    private LocalDate dateAcquired;

    @Column(name = "isbn_13_Publication")
    private Integer isbn13Publication;
}

