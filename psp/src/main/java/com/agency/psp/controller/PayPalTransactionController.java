package com.agency.psp.controller;

import com.agency.psp.PspApplication;
import com.agency.psp.dtos.PayPalTransactionDTO;
import com.agency.psp.model.PayPalTransaction;
import com.agency.psp.services.PayPalTransactionService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paypal")
public class PayPalTransactionController {

    private PayPalTransactionService service;
    final static Logger log = Logger.getLogger(PspApplication.class.getName());

    public PayPalTransactionController(PayPalTransactionService service) {
        this.service = service;
    }

    @PostMapping(value="/save")
    public ResponseEntity<?> save(@RequestBody PayPalTransactionDTO transaction){
      PayPalTransaction savedTransaction =  service.save(new PayPalTransaction(
                transaction.getPaymentId(),
                transaction.getPayerName(),
                transaction.getPayerLastname(),
                transaction.getPayerEmail(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getCurrency(),
                transaction.getPayeeEmail(),
                transaction.getPayeeName(),
                transaction.getDateAndTime()));
        log.info("Transaction with payment id " + transaction.getPaymentId() + " is saved. Transaction id: " + savedTransaction.getId());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
