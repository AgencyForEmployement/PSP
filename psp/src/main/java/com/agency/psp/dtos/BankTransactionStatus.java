package com.agency.psp.dtos;

import com.agency.psp.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankTransactionStatus {
    public int merchantId;
    public TransactionStatus status;
}
