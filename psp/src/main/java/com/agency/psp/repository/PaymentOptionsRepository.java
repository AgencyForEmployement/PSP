package com.agency.psp.repository;

import com.agency.psp.model.PaymentOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOptionsRepository extends JpaRepository<PaymentOptions, Long> {
}
