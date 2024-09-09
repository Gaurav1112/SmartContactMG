package com.sc.smartContact.helper;

import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

  public static String getEmailOfLoggedUser(Authentication authentication) {

    if (authentication instanceof OAuth2AuthenticationToken) {

      var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
      var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

      var oauth2User = (OAuth2User) authentication.getPrincipal();

      String userName = "";

      if (clientId.equalsIgnoreCase("google")) {

        userName = oauth2User.getAttribute("email").toString();

      } else if (clientId.equalsIgnoreCase("github")) {
        userName = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
            : oauth2User.getAttribute("login").toString() + "@gmail.com";

      }
      System.out.println(userName);
      return userName;

    } else {
      System.out.println(authentication.getName());
      return authentication.getName();
    }
  }

}
