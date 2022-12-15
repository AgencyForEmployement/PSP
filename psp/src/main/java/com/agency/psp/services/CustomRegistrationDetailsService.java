package com.agency.psp.services;

import com.agency.psp.model.Company;
import com.agency.psp.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomRegistrationDetailsService implements UserDetailsService {
    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String pib) throws UsernameNotFoundException {
        Company company = companyRepository.findByPib(pib);
        if(company == null){
            throw new UsernameNotFoundException(String.format("No company found with pib '%s'.", pib));
        }else{
            return company;
        }
    }
}
