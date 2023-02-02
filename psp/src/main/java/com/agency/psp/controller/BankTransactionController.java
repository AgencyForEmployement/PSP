package com.agency.psp.controller;
import com.agency.psp.dtos.BankTransactionStatus;
import com.agency.psp.dtos.PSPResponseDto;
import com.agency.psp.enums.TransactionStatus;
import com.agency.psp.model.BankTransaction;
import com.agency.psp.services.BankTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@RestController
@RequestMapping("/bank-transaction")
public class BankTransactionController {

    private final BankTransactionService bankTransactionService;
    private RestTemplate restTemplate;

    //ovo se gadja iz banke kada se zavrsi transakcija unutar iste banke
    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody PSPResponseDto bankTransaction){

        bankTransactionService.save(BankTransaction.builder()
                                                    .acquirerOrderId(bankTransaction.getAcquirerOrderId())
                                                    .amount(bankTransaction.getAmount())
                                                    .paymentId(bankTransaction.getPaymentId())
                                                    .transactionStatus(bankTransaction.getTransactionStatus())
                                                    .description(bankTransaction.getDescription())
                                                    .acquirerTimestamp(bankTransaction.getAcquirerTimestamp())
                                                    .merchantOrderId(bankTransaction.getMerchantOrderId())
                                                    .build()
        );
        //psp bi ovde trebao da obavesti webshop o stanju trannsakcije
        BankTransactionStatus body = BankTransactionStatus.builder().status(bankTransaction.getTransactionStatus()).merchantId(bankTransaction.getMerchantOrderId()).build();
     System.out.print(body);
      HttpStatus response =  restTemplate.postForObject("http://localhost:8082/transactions/status",
              body, HttpStatus.class);

       // int merchOrderId= bankTransactionService.findByPaymentId(bankTransaction.getPaymentId()).getMerchantOrderId();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
