package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Entity
@IdClass(ManyAccountHasManyPublication.ManyAccountHasManyPublicationId.class)
public class ManyAccountHasManyPublication {
    @Id
    @Column(name = "account_id_Account")
    private Integer accountIdAccount;

    @Id
    @Column(name = "isbn_13_Publication")
    private Integer isbn13Publication;

    @Column(name = "waitlist_position")
    private Short waitlistPosition;

    @ManyToOne
    @JoinColumn(name = "account_id_Account", insertable = false, updatable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "isbn_13_Publication", insertable = false, updatable = false)
    private Publication publication;

    // for JPA only, no use
    public ManyAccountHasManyPublication() {
    }

    // getters, setters and constructor

    public static class ManyAccountHasManyPublicationId implements Serializable {
        private Integer accountIdAccount;
        private Integer isbn13Publication;

        // getters, setters, equals, hashCode
    }
}
