package com.agency.psp.dtos;

import com.agency.psp.enums.TransactionStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PSPResponseDto {
    private int merchantOrderId;
    private int acquirerOrderId;
    private LocalDateTime acquirerTimestamp;
    private int paymentId;
    private double amount;
    private String description;
    private TransactionStatus transactionStatus;
}

