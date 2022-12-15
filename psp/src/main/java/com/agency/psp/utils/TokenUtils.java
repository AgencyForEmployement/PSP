package com.agency.psp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {
    @Value("psp")
    private String APP_NAME;

    @Value("somesecret")
    public String SECRET;

    @Value("86400000")
    private int EXPIRES_IN;

    @Value("Authorization")
    private String AUTH_HEADER;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateToken(String pib, String roleName) {
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(pib)
                .claim("role", roleName)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + EXPIRES_IN);
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public String getPibFromToken(String token) {
        String pib;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            pib = claims.getSubject();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            pib = null;
        }
        return pib;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            claims = null;
        }

        return claims;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String pib = getPibFromToken(token);
        return (pib != null  && pib.equals(userDetails.getUsername())); //pib is "username" in this case
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }
}
