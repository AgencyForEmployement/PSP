package com.agency.psp.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class PaymentOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column
    private boolean card;
    @Column
    private boolean qr;
    @Column
    private boolean paypal;
    @Column
    private boolean bitcoin;

    public PaymentOptions(boolean card, boolean qr, boolean paypal, boolean bitcoin) {
        this.card = card;
        this.qr = qr;
        this.paypal = paypal;
        this.bitcoin = bitcoin;
    }
}
