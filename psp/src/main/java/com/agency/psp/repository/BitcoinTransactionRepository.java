package com.agency.psp.repository;

import com.agency.psp.model.BitcoinTransaction;
import com.agency.psp.model.PayPalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitcoinTransactionRepository extends JpaRepository<BitcoinTransaction, Long> {

    BitcoinTransaction findByOrderId (String orderId);
}
