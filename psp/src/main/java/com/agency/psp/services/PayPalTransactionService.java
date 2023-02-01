package com.agency.psp.services;

import com.agency.psp.model.PayPalTransaction;
import com.agency.psp.repository.PayPalTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class PayPalTransactionService {

    private PayPalTransactionRepository repository;

    public PayPalTransactionService(PayPalTransactionRepository repository) {
        this.repository = repository;
    }

    public PayPalTransaction save(PayPalTransaction transaction) {
        return repository.save(transaction);
    }

    public PayPalTransaction findByPaymentId(String paymentId) {
        return repository.findByPaymentId(paymentId);
    }
}
