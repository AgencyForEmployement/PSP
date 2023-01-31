package com.agency.psp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDTO {
    private String price;
    private String description;
    private String pib;
    private String merchantOrderId;
    private String merchantOrderTimestamp; //ovo treba da bude ti LocalDateTime, nije jos toga bilo na web shopu pa sam stavila ovako zbog testiranja

}
