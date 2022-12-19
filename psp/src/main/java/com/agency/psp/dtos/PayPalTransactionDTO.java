package com.agency.psp.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class PayPalTransactionDTO {
    private String paymentId;
    private String payerName;
    private String payerLastname;
    private String payerEmail;
    private double amount;
    private String currency;
    private String payeeEmail;
    private String payeeName;
    private LocalDateTime dateAndTime;

    public PayPalTransactionDTO(String paymentId, String payerName, String payerLastname, String payerEmail, double amount, String currency, String payeeEmail, String payeeName, LocalDateTime dateAndTime) {
        this.paymentId = paymentId;
        this.payerName = payerName;
        this.payerLastname = payerLastname;
        this.payerEmail = payerEmail;
        this.amount = amount;
        this.currency = currency;
        this.payeeEmail = payeeEmail;
        this.payeeName = payeeName;
        this.dateAndTime = dateAndTime;
    }
}
