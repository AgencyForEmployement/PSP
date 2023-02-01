package com.agency.psp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Company implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column(unique = true)
    private String pib;
    @Column
    private String name;
    @Column(length = 100000)
    private String description;
    @Column
    private String password;
    @Column
    private String merchantId;
    @Column
    private String urlToBankAPI;
    @Column
    private String merchantPassword;
    @Column
    private String successUrl;
    @Column
    private String failedUrl;
    @Column
    private String errorUrl;
    @ManyToOne(fetch = FetchType.EAGER,cascade =  CascadeType.ALL)
    private Role role;
    @OneToOne(fetch = FetchType.EAGER,cascade =  CascadeType.ALL)
    private Address address;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> collection = new ArrayList<Role>();
        collection.add(this.role);
        return collection;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return pib;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
