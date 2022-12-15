package com.agency.psp.dtos;

import com.agency.psp.model.Address;
import com.agency.psp.model.Company;

public class CompanyDto {
    public long id;
    public String name;
    public String description;
    public String pib;
    public String password;
    public Address address;

    public CompanyDto(Company company) {
        this.id= company.getId();
        this.address = company.getAddress();
        this.name = company.getName();
        this.password = company.getPassword();
        this.pib = company.getPib();
        this.description = company.getDescription();
    }
}
