package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public final class AccountPublicationId implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "account_id_Account", nullable = false)
    private Integer accountIdAccount;

    @Column(name = "isbn_13_Publication", nullable = false)
    private Integer isbn13Publication;
}
