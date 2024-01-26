package com.shachi.shachihouse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shachi.shachihouse.utils.Provider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String username;
    private String email;
    private String fullname;
    @Lob
    @JsonIgnore
    private String password;
    @Lob
    private String images;
    @Temporal(TemporalType.DATE)
    private LocalDate createdate;
    @Temporal(TemporalType.DATE)
    private LocalDate updatedate;
    private String providerID;
    private Boolean isactive;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "authorities",
            joinColumns = @JoinColumn(name = "accountId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles = new HashSet<>();



}
