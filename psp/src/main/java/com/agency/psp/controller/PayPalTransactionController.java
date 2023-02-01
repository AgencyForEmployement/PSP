package com.agency.psp.controller;

import com.agency.psp.PspApplication;
import com.agency.psp.dtos.OrderDTO;
import com.agency.psp.dtos.PayPalOrderDto;
import com.agency.psp.dtos.PayPalPaymentDTO;
import com.agency.psp.dtos.PayPalTransactionDTO;
import com.agency.psp.model.PayPalTransaction;
import com.agency.psp.services.PayPalTransactionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/paypal")
public class PayPalTransactionController {

    @Autowired
    private RestTemplate restTemplate;
    private PayPalTransactionService service;
    final static Logger log = Logger.getLogger(PspApplication.class.getName());

    public PayPalTransactionController(PayPalTransactionService service) {
        this.service = service;
    }

    @PostMapping(value="/send")
    public ResponseEntity<PayPalPaymentDTO> send(@RequestBody PayPalOrderDto order){
        PayPalTransaction t = new PayPalTransaction(
                "",
                "",
                "",
                "",
                order.getPrice(),
                order.getDescription(),
                "EUR",
                "",
                "",
                LocalDateTime.now(),
                "");
        PayPalPaymentDTO res = restTemplate.postForObject("http://localhost:9090/pay", order, PayPalPaymentDTO.class);
        t.setPaymentId(res.getPaymentId());
        t.setState(res.getStatus());
        service.save(t);
        log.info("Transaction with payment id " + t.getPaymentId() + " is saved. Transaction id: " + t.getId());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(value="/update")
    public ResponseEntity<?> update(@RequestBody PayPalTransactionDTO transaction){
        System.out.println(transaction.toString());
      PayPalTransaction t =  service.findByPaymentId(transaction.getPaymentId());
        t.setPayerName(transaction.getPayerName());
        t.setPayerLastname(transaction.getPayerLastname());
        t.setPayerEmail(transaction.getPayerEmail());
        t.setPayeeEmail(transaction.getPayeeEmail());
        t.setPayeeName(transaction.getPayeeName());
        t.setState(transaction.getState());
        service.save(t);
        log.info("Transaction with payment id " + transaction.getPaymentId() + " has updated it's state to " + transaction.getState() + ". " + "Transaction id: " + transaction.getPaymentId());
        PayPalPaymentDTO dto = new PayPalPaymentDTO(t.getPaymentId(), "link", t.getState());
        System.out.println(dto);
        String res = restTemplate.postForObject("http://localhost:8082/transactions/paypalUpdate", dto, String.class);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
