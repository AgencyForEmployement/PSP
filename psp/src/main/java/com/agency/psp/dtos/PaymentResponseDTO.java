package com.agency.psp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private String paymentURL;
    private int paymentId;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;
}
