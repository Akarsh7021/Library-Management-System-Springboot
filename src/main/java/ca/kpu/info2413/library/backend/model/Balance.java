package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Balance")
@Data
@NoArgsConstructor
public class Balance
{
    @Id
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "total_fines")
    private Integer totalFines;

    @Column(name = "total_payments")
    private Integer totalPayments;

    @Column(name = "current_balance")
    private Integer currentBalance;
}
