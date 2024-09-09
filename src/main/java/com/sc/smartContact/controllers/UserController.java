package com.sc.smartContact.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sc.smartContact.entity.User;
import com.sc.smartContact.helper.Helper;
import com.sc.smartContact.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserService userService;

  // user dashboard page
  @RequestMapping("/dashboard")
  public String requestMethodName() {
    System.out.println("User Dashboard!!");
    return "user/dashboard";
  }

  // user profile page

  @RequestMapping("/profile")
  public String userProfile(Authentication authentication) {
    String name = Helper.getEmailOfLoggedUser(authentication);
    log.info("name: {}", name);
    // database fetch

    User user = userService.getUserByEmail(name);
    log.info("UserName: {} UserEmail: {}", user.getName(), user.getEmail());

    return "user/profile";
  }

  // user add contacts

  // user view contacts

}
