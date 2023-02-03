package com.agency.psp.services;

import com.agency.psp.PspApplication;
import com.agency.psp.dtos.OrderDTO;
import com.agency.psp.dtos.PaymentForBankRequestDto;
import com.agency.psp.dtos.PaymentResponseDTO;
import com.agency.psp.enums.PaymentURLType;
import com.agency.psp.model.Company;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class BankService {
    private final CompanyService companyService;
    final static Logger log = Logger.getLogger(PspApplication.class.getName());

    public PaymentForBankRequestDto payWithCard(OrderDTO order) {
        log.info("Paying with credit card in process...");
        Company company = companyService.findByPib(order.getPib());
        PaymentForBankRequestDto paymentForBankRequestDto = new PaymentForBankRequestDto();

        paymentForBankRequestDto.setMerchantId(company.getMerchantId());
        paymentForBankRequestDto.setMerchantPassword(company.getMerchantPassword());
        paymentForBankRequestDto.setAmount(Double.parseDouble(order.getPrice()));
        paymentForBankRequestDto.setMerchantOrderId(Integer.parseInt(order.getMerchantOrderId()));
        paymentForBankRequestDto.setMerchantTimestamp(LocalDateTime.now());
        paymentForBankRequestDto.setSuccessUrl(company.getSuccessUrl());
        paymentForBankRequestDto.setErrorUrl(company.getErrorUrl());
        paymentForBankRequestDto.setFailedUrl(company.getFailedUrl());
        paymentForBankRequestDto.setDescription(order.getDescription());
        log.info("Payment finished with merchant order " + paymentForBankRequestDto.getMerchantOrderId());
        return paymentForBankRequestDto;
    }
}
