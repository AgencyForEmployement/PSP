package com.agency.psp.repository;

import com.agency.psp.model.PayPalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayPalTransactionRepository extends JpaRepository <PayPalTransaction, Long> {

    PayPalTransaction findByPaymentId(String paymentId);

}
