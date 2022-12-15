package com.agency.psp.dtos;

public class CompanyTokenStateDto {
    public String accessToken;
    public String role;

    public CompanyTokenStateDto(String accessToken, String role) {
        this.accessToken = accessToken;
        this.role = role;
    }
}
