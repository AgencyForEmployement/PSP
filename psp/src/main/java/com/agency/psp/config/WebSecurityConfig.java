package com.agency.psp.config;

import com.agency.psp.security.RestAuthenticationEntryPoint;
import com.agency.psp.security.TokenAuthenticationFilter;
import com.agency.psp.services.CustomRegistrationDetailsService;
import com.agency.psp.utils.TokenUtils;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomRegistrationDetailsService customRegistrationDetailsService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final TokenUtils tokenUtils;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customRegistrationDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .authorizeRequests().antMatchers("/authentication/**").permitAll().and()
                .authorizeRequests().antMatchers("/paypal/**").permitAll().and()
                .authorizeRequests().antMatchers("/bitcoin/**").permitAll().and()
                .antMatchers("/bank/**").permitAll()

                .anyRequest().authenticated().and()
                .cors().and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, customRegistrationDetailsService), BasicAuthenticationFilter.class);

        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers(HttpMethod.POST, "/authentication/login")
                .antMatchers(HttpMethod.POST, "/authentication/registration")
                .antMatchers(HttpMethod.POST, "/paypal/update")
                .antMatchers(HttpMethod.POST, "/bitcoin/savetransaction")
                .antMatchers(HttpMethod.POST, "/bitcoin/updatetransaction")
                .antMatchers(HttpMethod.POST, "/bank-transaction")
                .antMatchers(HttpMethod.POST, "/paypal/save");
    }
}
