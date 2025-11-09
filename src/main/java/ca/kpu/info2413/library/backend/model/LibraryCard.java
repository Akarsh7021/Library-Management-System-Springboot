package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class LibraryCard
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_number")
    private Integer cardNumber;

    private boolean valid;

    @Column(name = "account_id_Account")
    private Integer accountIdAccount;
}