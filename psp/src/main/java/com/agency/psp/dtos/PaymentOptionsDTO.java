package com.agency.psp.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentOptionsDTO {
    private long id;
    private boolean card;
    private boolean qr;
    private boolean paypal;
    private boolean bitcoin;

}
