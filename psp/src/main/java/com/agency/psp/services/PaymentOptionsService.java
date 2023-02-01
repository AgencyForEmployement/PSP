package com.agency.psp.services;

import com.agency.psp.model.Company;
import com.agency.psp.model.PaymentOptions;
import com.agency.psp.repository.PaymentOptionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Random;

@Service
@AllArgsConstructor
public class PaymentOptionsService {

    private final PaymentOptionsRepository repository;

    public PaymentOptions save(PaymentOptions options){
        return repository.save(options);
    }

    public PaymentOptions findById(long id) { return repository.findById(id).get(); }
}
