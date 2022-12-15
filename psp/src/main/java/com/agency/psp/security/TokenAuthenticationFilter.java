package com.agency.psp.security;

import com.agency.psp.services.CustomRegistrationDetailsService;
import com.agency.psp.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;
    private final CustomRegistrationDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String pib;
    String authToken = tokenUtils.getToken(request);
    
        if (authToken != null) {
            pib = tokenUtils.getPibFromToken(authToken);
            if (pib != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(pib);
                if (tokenUtils.validateToken(authToken, userDetails)) {
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                    authentication.setToken(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
