package com.sc.smartContact.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sc.smartContact.entity.User;
import com.sc.smartContact.forms.UserForm;
import com.sc.smartContact.helper.Message;
import com.sc.smartContact.helper.MessageType;
import com.sc.smartContact.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

  @Autowired
  private UserService userService;

  @GetMapping("/")
  public String index() {
    return "redirect:/home";
  }

  @RequestMapping("/home")
  public String home(Model model) {
    model.addAttribute("name", "Home Page");
    model.addAttribute("project", "Smart Contact Management");
    model.addAttribute("details", "Working with thymleaf");
    model.addAttribute("Youtube", "https://www.youtube.com/");
    return "home";
  }

  // about
  @RequestMapping("/about")
  public String aboutPage(Model model) {
    model.addAttribute("isLogin", true);
    return "about";
  }

  @RequestMapping("/services")
  public String servicesPage() {
    return "services";
  }

  @RequestMapping("/contact")
  public String contactPage() {
    return "contact";
  }

  @RequestMapping("/login")
  public String LoginPage() {
    return "login";
  }

  @RequestMapping("/signup")
  public String signUpPage(Model model) {

    UserForm userForm = new UserForm();
    model.addAttribute("userForm", userForm);
    return "signup";
  }

  @RequestMapping(value = "/do-register", method = RequestMethod.POST)
  public String processing(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult,
      HttpSession session) {
    System.out.println(userForm);

    if (bindingResult.hasErrors()) {
      return "signup";
    }
    // validate form data

    // save to database
    User user = new User();
    user.setName(userForm.getName());
    user.setEmail(userForm.getEmail());
    user.setPhoneNumber(userForm.getPhoneNumber());
    user.setAbout(userForm.getAbout());
    user.setPassword(userForm.getPassword());
    userService.savUser(user);
    System.out.println("User Saved!!");

    // validate Data

    // add the message
    Message message = Message.builder().content("Registration Successful")
        .type(MessageType.green).build();
    session.setAttribute("message", message);

    return "redirect:/signup";
  }

}
