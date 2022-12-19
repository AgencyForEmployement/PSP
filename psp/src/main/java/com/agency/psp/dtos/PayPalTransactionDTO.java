package com.agency.psp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PayPalTransactionDTO {
    private String paymentId;
    private String payerName;
    private String payerLastname;
    private String payerEmail;
    private double amount;
    private String description;
    private String currency;
    private String payeeEmail;
    private String payeeName;
    private LocalDateTime dateAndTime;


}
