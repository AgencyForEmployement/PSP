package com.agency.psp.controller;

import com.agency.psp.PspApplication;
import com.agency.psp.dtos.*;
import com.agency.psp.model.Company;
import com.agency.psp.model.PaymentOptions;
import com.agency.psp.services.CompanyService;
import com.agency.psp.services.PaymentOptionsService;
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
    private final PaymentOptionsService paymentOptionsService;

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
        return ResponseEntity.ok(new CompanyTokenStateDto(jwt, company.getRole().getRoleName(),company.getId(), company.getApiKey()));
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

    @PostMapping("/changePayments")
    public ResponseEntity<String> changePayments(@RequestBody PaymentOptionsDTO options) {
        Company c = companyService.findById(options.getId());
        PaymentOptions ops = paymentOptionsService.findById(c.getPaymentOptions().getId());
        ops.setCard(options.isCard());
        ops.setQr(options.isQr());
        ops.setBitcoin(options.isBitcoin());
        ops.setPaypal(options.isPaypal());
        paymentOptionsService.save(ops);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/getPayments/{id}")
    public ResponseEntity<?> changePayments(@PathVariable long id) {
        Company c = companyService.findById(id);
        return new ResponseEntity<>(c.getPaymentOptions(), HttpStatus.OK);
    }

    @PostMapping("/getCompanyPayments")
    public ResponseEntity<?> changePayments(@RequestBody OrderIdDTO dto, @RequestHeader("Authorization") String token) {
        Company c = companyService.findByApiKey(token.split(" ")[1]);
        System.out.println(token.split(" ")[1]);
        if (c != null) {
            return new ResponseEntity<>(c.getPaymentOptions(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
