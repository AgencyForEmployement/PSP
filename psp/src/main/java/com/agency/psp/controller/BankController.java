package com.agency.psp.controller;

import com.agency.psp.PspApplication;
import com.agency.psp.dtos.OrderDTO;
import com.agency.psp.dtos.PaymentForBankRequestDto;
import com.agency.psp.dtos.PaymentResponseDTO;
import com.agency.psp.model.Company;
import com.agency.psp.services.BankService;
import com.agency.psp.services.CompanyService;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
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
    private final CompanyService companyService;
    private final RestTemplate restTemplate;
    final static Logger log = Logger.getLogger(PspApplication.class.getName());

    //kada neko odabere na forontu placanje karticom
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> save(@RequestBody OrderDTO order){
        PaymentForBankRequestDto paymentForBankRequestDto = bankService.payWithCard(order);
        Company company = companyService.findByPib(order.getPib()); //http://localhost:8083/payment
        log.info("Payment with credit card requested");
        log.info("Order with order id " + order.getMerchantOrderId());
        PaymentResponseDTO response = restTemplate.postForObject("http://localhost:8083/payment", paymentForBankRequestDto, PaymentResponseDTO.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
