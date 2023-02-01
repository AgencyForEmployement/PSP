package com.agency.psp.services;

import com.agency.psp.model.BankTransaction;
import com.agency.psp.repository.BankTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BankTransactionService {

    private final BankTransactionRepository bankTransactionRepository;

    public void save(BankTransaction bankTransaction) {
        bankTransactionRepository.save(bankTransaction);
    }
}
