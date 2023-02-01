package com.agency.psp.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data
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
    private String state;


}
