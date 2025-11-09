package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AccountPublication")
@Getter
@Setter
@ToString(exclude = {"account", "publication"}) // avoid lazy-loading / recursion in toString
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA requires a no-arg ctor (protected is fine)
@AllArgsConstructor
@Builder
public class AccountPublication
{
    @EmbeddedId
    private AccountPublicationId id;

    @MapsId("accountIdAccount")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id_account", nullable = false)
    private Account account;

    @MapsId("isbn13Publication")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "isbn_13_publication", nullable = false)
    private Publication publication;

    @Column(name = "waitlist_position")
    private Integer waitlistPosition;
}
