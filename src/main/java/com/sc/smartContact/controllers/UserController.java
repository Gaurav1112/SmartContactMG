package com.sc.smartContact.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

  // user dashboard page
  @RequestMapping("/dashboard")
  public String requestMethodName() {
    System.out.println("User Dashboard!!");
    return "user/dashboard";
  }

  // user profile page

  @RequestMapping("/profile")
  public String userProfile() {
    System.out.println("User Dashboard!!");
    return "user/profile";
  }

  // user add contacts

  // user view contacts

}
