package com.agency.psp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;

    @Column
    private String roleName; //uvek ce biti jedna rola, tj kompanija?

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY,cascade =  CascadeType.PERSIST)
    @JsonIgnore
    private List<Company> companies = new ArrayList<>();

    @Override
    public String getAuthority() {
        return roleName.toString();
    }

}
