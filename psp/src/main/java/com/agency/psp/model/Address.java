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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column
    private String city;
    @Column
    private String street;
    @Column
    private String number;
    @Column
    private String country;
}
