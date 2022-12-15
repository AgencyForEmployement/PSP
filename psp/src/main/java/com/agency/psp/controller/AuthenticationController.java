package com.agency.psp.controller;

import com.agency.psp.dtos.AuthenticationRequestDto;
import com.agency.psp.dtos.CompanyDto;
import com.agency.psp.dtos.CompanyTokenStateDto;
import com.agency.psp.model.Company;
import com.agency.psp.services.CompanyService;
import com.agency.psp.utils.TokenUtils;
import lombok.AllArgsConstructor;
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

    @PostMapping("/login")
    public ResponseEntity<CompanyTokenStateDto> login(@RequestBody AuthenticationRequestDto authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.pib, authenticationRequest.password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Company company = (Company)authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(company.getPib(), company.getRole().getRoleName());

        return ResponseEntity.ok(new CompanyTokenStateDto(jwt, company.getRole().getRoleName()));
    }

    @PostMapping("/registration")
    public ResponseEntity<CompanyDto> register(@RequestBody Company newCompany) {
        if(companyService.findByPib(newCompany.getPib()) !=null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CompanyDto company = new CompanyDto(companyService.save(newCompany));
        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }
}
