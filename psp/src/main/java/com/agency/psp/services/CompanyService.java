package com.agency.psp.services;

import com.agency.psp.model.Company;
import com.agency.psp.model.PaymentOptions;
import com.agency.psp.model.Role;
import com.agency.psp.repository.CompanyRepository;
import com.agency.psp.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Random;

@Service
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    public Company save(Company company){
        company.setRole(roleRepository.findByRoleName("ROLE_COMPANY"));
        company.setPassword(bCryptPasswordEncoder.encode(company.getPassword()));
        company.setPaymentOptions(new PaymentOptions(false, false, false, false));
        //byte[] array = new byte[16];
        //new Random().nextBytes(array);
        //String generatedString = new String(array, Charset.forName("UTF-8"));
        String generatedString = "1a2s3d4f5g6h7j8k";
        company.setApiKey(generatedString);
        return companyRepository.save(company);
    }

    public Company findByPib(String pib) {
        return companyRepository.findByPib(pib);
    }

    public Company findById(long id) { return companyRepository.findById(id); }

    public Company findByApiKey (String apiKey) {return companyRepository.findByApiKey(apiKey);}

}
