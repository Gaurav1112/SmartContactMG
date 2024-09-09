package com.sc.smartContact.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sc.smartContact.entity.User;
import com.sc.smartContact.helper.Helper;
import com.sc.smartContact.services.UserService;

@ControllerAdvice
public class RootController {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserService userService;

  @ModelAttribute
  public void addLoggedInUserInformation(Authentication authentication, Model model) {

    if (authentication == null)
      return;

    String name = Helper.getEmailOfLoggedUser(authentication);
    log.info("name: {}", name);
    // database fetch

    User user = userService.getUserByEmail(name);
    model.addAttribute("loggedInUser", user);
    log.info("UserName: {} UserEmail: {}", user.getName(), user.getEmail());
    model.addAttribute("loggedInUser", user);
  }

}
