package com.agency.psp.controller;

import com.agency.psp.PspApplication;
import com.agency.psp.dtos.AuthenticationRequestDto;
import com.agency.psp.dtos.CompanyDto;
import com.agency.psp.dtos.CompanyTokenStateDto;
import com.agency.psp.model.Company;
import com.agency.psp.services.CompanyService;
import com.agency.psp.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final CompanyService companyService;
    final static Logger log = Logger.getLogger(PspApplication.class.getName());

    @PostMapping("/login")
    public ResponseEntity<CompanyTokenStateDto> login(@RequestBody AuthenticationRequestDto authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.pib, authenticationRequest.password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Company company = (Company)authentication.getPrincipal();
        if(company == null){
            log.error("Login failed.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String jwt = tokenUtils.generateToken(company.getPib(), company.getRole().getRoleName());
        log.info("User with user id " + company.getId() + " is logged in.");
        return ResponseEntity.ok(new CompanyTokenStateDto(jwt, company.getRole().getRoleName()));
    }

    @PostMapping("/registration")
    public ResponseEntity<CompanyDto> register(@RequestBody Company newCompany) {
        if(companyService.findByPib(newCompany.getPib()) != null){
            log.error("New registration failed! The company with this PIB is already registered!");
            log.warn("Duplicate registration request for company with id " + companyService.findByPib(newCompany.getPib()).getId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CompanyDto company = new CompanyDto(companyService.save(newCompany));
        log.info("New company successfully registered with id " + company.id);
        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }
}
