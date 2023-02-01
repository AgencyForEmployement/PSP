package com.agency.psp.model;

import com.agency.psp.enums.TransactionStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column
    private int merchantOrderId;
    @Column
    private int acquirerOrderId;
    @Column
    private LocalDateTime acquirerTimestamp;
    @Column
    private int paymentId;
    @Column
    private double amount;
    @Column
    private String description;
    @Column
    private TransactionStatus transactionStatus;
}
