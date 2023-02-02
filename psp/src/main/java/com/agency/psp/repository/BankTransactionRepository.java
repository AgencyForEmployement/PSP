package com.agency.psp.repository;

import com.agency.psp.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

    BankTransaction findByPaymentId(int paymentId);
}
