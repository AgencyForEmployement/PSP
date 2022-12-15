package com.agency.psp.repository;

import com.agency.psp.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByPib(String pib);
    Company findById(long id);
}
