package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LibraryCard")
@Data
@NoArgsConstructor
public class LibraryCard
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_number")
    private Integer cardNumber;

    // keep column name as-is (DB uses boolean/bit/ tinyint depending on DB)
    @Column(name = "valid")
    private Boolean valid;

    // Proper JPA relation to Account using the existing FK column name in your DB
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id_Account") // this is the FK column in LibraryCard table
    private Account account;
}