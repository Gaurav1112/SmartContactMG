package com.sc.smartContact.services;

import com.sc.smartContact.entity.User;
import java.util.*;

public interface UserService {

  User savUser(User user);

  Optional<User> getUserById(String id);

  Optional<User> updatUser(User user);

  void deleteUser(String id);

  boolean isUserExists(String userId);

  boolean isUserExistsByEmail(String email);

  List<User> getAllUsers();

}
