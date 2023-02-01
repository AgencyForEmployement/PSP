package com.agency.psp.services;

import com.agency.psp.model.BitcoinTransaction;
import com.agency.psp.repository.BitcoinTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class BitcoinTransactionService {

    private BitcoinTransactionRepository repository;

    public BitcoinTransactionService(BitcoinTransactionRepository repository) {
        this.repository = repository;
    }

    public BitcoinTransaction save(BitcoinTransaction transaction) {
        return repository.save(transaction);
    }

    public BitcoinTransaction findByOrderId(String orderId) { return repository.findByOrderId(orderId); }
}
