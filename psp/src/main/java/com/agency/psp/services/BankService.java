package com.agency.psp.services;

import com.agency.psp.dtos.OrderDTO;
import com.agency.psp.dtos.PaymentForBankRequestDto;
import com.agency.psp.dtos.PaymentResponseDTO;
import com.agency.psp.enums.PaymentURLType;
import com.agency.psp.model.Company;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class BankService {
    private final CompanyService companyService;
    public PaymentForBankRequestDto payWithCard(OrderDTO order) {
        Company company = companyService.findByPib(order.getPib());
        PaymentForBankRequestDto paymentForBankRequestDto = new PaymentForBankRequestDto();

        paymentForBankRequestDto.setMerchantId(company.getMerchantId());
        paymentForBankRequestDto.setMerchantPassword(company.getMerchantPassword());
        paymentForBankRequestDto.setAmount(Double.parseDouble(order.getPrice()));
        paymentForBankRequestDto.setMerchantOrderId(Integer.parseInt(order.getMerchantOrderId()));
        paymentForBankRequestDto.setMerchantTimestamp(LocalDateTime.now());  //ovo uzeti zapravo iz order-a kada ne bude string
        paymentForBankRequestDto.setSuccessUrl(companyService.findUrl(PaymentURLType.SUCCESS, company));
        paymentForBankRequestDto.setErrorUrl(companyService.findUrl(PaymentURLType.ERROR, company));
        paymentForBankRequestDto.setFailedUrl(companyService.findUrl(PaymentURLType.FAILED, company));
        return paymentForBankRequestDto;
    }
}
