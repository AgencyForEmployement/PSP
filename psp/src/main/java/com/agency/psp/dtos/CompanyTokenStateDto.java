package com.agency.psp.dtos;

public class CompanyTokenStateDto {
    public String accessToken;
    public String role;
    public long id;
    public String apiKey;

    public CompanyTokenStateDto(String accessToken, String role, long id, String apiKey) {
        this.accessToken = accessToken;
        this.role = role;
        this.id = id;
        this.apiKey = apiKey;
    }
}
