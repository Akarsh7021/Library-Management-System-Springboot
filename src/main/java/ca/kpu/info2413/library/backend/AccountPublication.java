package ca.kpu.info2413.library.backend;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AccountPublication
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "waitlist_position")
    private Integer waitlistPosition;

    @Column(name = "account_id_Account")
    private Integer accountIdAccount;

    @Column(name = "isbn_13_Publication")
    private Integer isbn13Publication;
}
