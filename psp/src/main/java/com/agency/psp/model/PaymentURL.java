package com.agency.psp.model;

import com.agency.psp.enums.PaymentURLType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentURL {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column
    private String URL;
    @Column
    private PaymentURLType paymentURLType;
    @ManyToOne(fetch = FetchType.LAZY,cascade =  CascadeType.ALL)
    @JsonIgnore
    private Company company;
}
