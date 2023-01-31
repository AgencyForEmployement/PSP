package com.agency.psp.controller;

import com.agency.psp.dtos.OrderDTO;
import com.agency.psp.dtos.PaymentForBankRequestDto;
import com.agency.psp.dtos.PaymentResponseDTO;
import com.agency.psp.services.BankService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@AllArgsConstructor
@RequestMapping("/bank")
public class BankController {
    private final BankService bankService;
    private final RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> save(@RequestBody OrderDTO order){
        PaymentForBankRequestDto paymentForBankRequestDto = bankService.payWithCard(order);

        return new ResponseEntity<>(restTemplate.postForObject("http://localhost:8083/payment", paymentForBankRequestDto, PaymentResponseDTO.class), HttpStatus.OK);
    }

}
