package com.sc.smartContact.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// how to excluse methods from lombok @Getter(ACCESSLEVEL.NONE), it can be given
// in the variable level
public class User implements UserDetails {
  @Id
  private String userId;
  private String name;
  @Column(unique = true, nullable = false)
  private String email;
  private String password;
  @Column(length = 8000)
  private String about;
  @Column(length = 1000)
  private String profilePic;
  private String phoneNumber;

  // meta information
  private boolean enabled = true;
  private boolean emailVerified = false;
  private boolean phoneVerified = false;

  // User signup -- SELF/GOOGLE/GIT Hub or how
  @Enumerated(value = EnumType.STRING)
  private Providers provider = Providers.SELF;
  private String providerId;

  // Cascade says is user updated related gets updated
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<Contact> contacts = new ArrayList<>();

  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roleList = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // collection of SimpleGrantedAuthority[roles{ADMIN,USER}]
    Collection<SimpleGrantedAuthority> grantedRoles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());
    return grantedRoles;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

}
