package com.sc.smartContact.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sc.smartContact.entity.User;
import com.sc.smartContact.repositories.UserRepository;
import com.sc.smartContact.services.UserService;
import com.sc.smartContact.helper.*;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public User savUser(User user) {

    String userId = UUID.randomUUID().toString();
    user.setUserId(userId);

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    user.setRoleList(List.of(AppConstants.ROLE_USER));
    return userRepository.save(user);
  }

  @Override
  public Optional<User> getUserById(String id) {

    return userRepository.findById(id);
  }

  @Override
  public Optional<User> updatUser(User user) {

    User userReturned = userRepository.findById(user.getUserId())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    userReturned.setName(user.getName());
    userReturned.setEmail(user.getEmail());
    userReturned.setPassword(user.getPassword());
    userReturned.setAbout(user.getAbout());
    userReturned.setPhoneNumber(user.getPhoneNumber());
    userReturned.setProfilePic(user.getProfilePic());
    userReturned.setEnabled(user.isEnabled());
    userReturned.setEmailVerified(user.isEmailVerified());
    userReturned.setPhoneVerified(user.isPhoneVerified());
    userReturned.setProvider(user.getProvider());
    userReturned.setProviderId(user.getProviderId());

    // save the user in DB
    User savedUser = userRepository.save(userReturned);

    return Optional.ofNullable(savedUser);

  }

  @Override
  public void deleteUser(String id) {
    User userReturned = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    userRepository.delete(userReturned);
  }

  @Override
  public boolean isUserExists(String userId) {
    User userReturned = userRepository.findById(userId).orElse(null);

    return userReturned != null ? true : false;
  }

  @Override
  public boolean isUserExistsByEmail(String email) {
    User userReturned = userRepository.findByEmail(email).orElse(null);

    return userReturned != null ? true : false;
  }

  @Override
  public List<User> getAllUsers() {

    return userRepository.findAll();
  }

}
