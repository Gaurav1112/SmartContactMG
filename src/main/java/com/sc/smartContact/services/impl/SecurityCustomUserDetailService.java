package com.sc.smartContact.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sc.smartContact.repositories.UserRepository;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // load our user
    return userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with this Email"));

  }

}
