package com.sc.smartContact;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sc.smartContact.entity.Providers;
import com.sc.smartContact.entity.User;
import com.sc.smartContact.helper.AppConstants;
import com.sc.smartContact.repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Oauth2success implements AuthenticationSuccessHandler {

  @Autowired
  private UserRepository userRepository;

  Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    System.out.println("oAuthSuccessHandler");

    DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

    var oauth2AuthenticationTokken = (OAuth2AuthenticationToken) authentication;

    String authorizedClient = oauth2AuthenticationTokken.getAuthorizedClientRegistrationId();

    log.info("authorizedClient: {}", authorizedClient);

    System.out.println("user: " + user);
    // can save in database

    if (authorizedClient.equals("google")) {
      String email = user.getAttribute("email").toString();
      String name = user.getAttribute("name").toString();
      String picture = user.getAttribute("picture").toString();

      // create user and save it
      User user1 = new User();
      user1.setEmail(email);
      user1.setName(name);
      user1.setProfilePic(picture);
      user1.setPassword("password");
      user1.setUserId(UUID.randomUUID().toString());
      user1.setProvider(Providers.GOOGLE);
      user1.setEnabled(true);
      user1.setEmailVerified(true);
      user1.setProviderId(user.getName());
      user1.setRoleList(List.of(AppConstants.ROLE_USER));
      user1.setAbout("This is created using google Oath2");
      User user2 = userRepository.findByEmail(email).orElse(null);
      if (user2 == null) {
        userRepository.save(user1);
      }
    } else if (authorizedClient.equals("github")) {
      log.info("user Details: {}", user.getAttributes());
      String email = user.getAttribute("html_url");
      String name = user.getAttribute("name").toString();
      String picture = user.getAttribute("avatar_url").toString();

      User user1 = new User();
      user1.setEmail(email);
      user1.setName(name);
      user1.setProfilePic(picture);
      user1.setPassword("password");
      user1.setUserId(UUID.randomUUID().toString());
      user1.setProvider(Providers.GITHUB);
      user1.setEnabled(true);
      user1.setEmailVerified(true);
      user1.setProviderId(user.getName());
      user1.setRoleList(List.of(AppConstants.ROLE_USER));
      user1.setAbout("This is created using google Oath2");
      User user2 = userRepository.findByEmail(email).orElse(null);
      if (user2 == null) {
        userRepository.save(user1);
      }
    }

    response.sendRedirect("/user/profile");

  }

}
