package com.agency.psp.controller;

import com.agency.psp.PspApplication;
import com.agency.psp.dtos.BitcoinPaymentInfoDTO;
import com.agency.psp.dtos.OrderIdDTO;
import com.agency.psp.dtos.UpdateBitcoinTransactionDTO;
import com.agency.psp.model.BitcoinTransaction;
import com.agency.psp.services.BitcoinTransactionService;
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
@RequestMapping("/bitcoin")
public class BitcoinTransactionController {

    private BitcoinTransactionService service;
    final static Logger log = Logger.getLogger(PspApplication.class.getName());
    @Autowired
    private RestTemplate restTemplate;

    public BitcoinTransactionController(BitcoinTransactionService service) {
        this.service = service;
    }

    @PostMapping(value="/savetransaction")
    public ResponseEntity<?> save(@RequestBody BitcoinPaymentInfoDTO info){
        BitcoinTransaction savedTransaction = service.save(new BitcoinTransaction(
                "",
                info.getOrderId(),
                "new",
                info.getTitle(),
                info.getPriceAmount(),
                "",
                "",
                info.getPriceCurrency(),
                "",
                0,
                LocalDateTime.now(),
                ""
        ));
        String redirectionLink = restTemplate.postForObject("http://localhost:9092/bitcoin/pay", info, String.class);
        log.info("Crypto transaction with payment id " + info.getOrderId()+ " is created. Transaction id: " + savedTransaction.getId());
        return new ResponseEntity<>(redirectionLink, HttpStatus.OK);
    }

    @PostMapping(value="/updatetransaction")
    public ResponseEntity<?> update(@RequestBody UpdateBitcoinTransactionDTO info){
        BitcoinTransaction transaction = service.findByOrderId(info.getOrderId());
        transaction.setPaymentId(info.getPaymentId());
        transaction.setStatus(info.getStatus());
        transaction.setOrderableType(info.getOrderableType());
        transaction.setOrderableId(info.getOrderableId());
        transaction.setCryptoCurrency(info.getCryptoCurrency());
        transaction.setCryptoAmount(info.getCryptoAmount());
        transaction.setToken(info.getToken());
        System.out.println(transaction);
        service.save(transaction);
        log.info("Crypto transaction with payment id " + info.getOrderId()+ " has updated it's status to " + info.getStatus() + ". Transaction id: " + transaction.getId());
        if (info.getStatus().equalsIgnoreCase("paid")) {
            String res = restTemplate.postForObject("http://localhost:8082/transactions/bitcoinUpdate", new OrderIdDTO(info.getOrderId()), String.class);
        }                                   //staviti webshop localhost port
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
