package com.agency.psp.services;

import com.agency.psp.enums.PaymentURLType;
import com.agency.psp.model.Company;
import com.agency.psp.model.PaymentURL;
import com.agency.psp.model.Role;
import com.agency.psp.repository.CompanyRepository;
import com.agency.psp.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    public Company save(Company company){
       company.setRole(roleRepository.findByRoleName("ROLE_COMPANY"));
        company.setPassword(bCryptPasswordEncoder.encode(company.getPassword()));
        return companyRepository.save(company);
    }

    public Company findByPib(String pib) {
        return companyRepository.findByPib(pib);
    }

    public PaymentURL findUrl(PaymentURLType type, Company company) {
        for (PaymentURL paymentURL: company.getPaymentURLS()
             ) {
            if (paymentURL.getPaymentURLType() == type)
                return paymentURL;
        }
        return null;
    }
}
