package com.sc.smartContact;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.sc.smartContact.services.impl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

  @Autowired
  private SecurityCustomUserDetailService userDetailService;

  @Autowired
  private Oauth2success oauth2Success;

  // configuration of authentication Provider
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    // configure URLS are public and which are private for authentication
    httpSecurity.authorizeHttpRequests(authorize -> {
      // authorize.requestMatchers("/home", "/signup", "/services").permitAll();
      authorize.requestMatchers("/user/**").authenticated();
      authorize.anyRequest().permitAll();
    });
    // form default login
    // if anything needs to be changed, we will come here for form login releated
    // if not use default httpSecurity.formLogin(Customizer.withDefault())
    httpSecurity.formLogin(formLogin -> {
      formLogin.loginPage("/login");
      formLogin
          .loginProcessingUrl("/authenticate")
          .successForwardUrl("/user/dashboard");
      // .failureForwardUrl("/login?error=true");
      formLogin.usernameParameter("email")
          .passwordParameter("password");
      /*
       * formLogin.failureHandler(new AuthenticationFailureHandler() {
       * 
       * @Override
       * public void onAuthenticationFailure(HttpServletRequest request,
       * HttpServletResponse response,
       * AuthenticationException exception) throws IOException, ServletException {
       * // TODO Auto-generated method stub
       * throw new
       * UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'"
       * );
       * }
       * 
       * });
       */

      /*
       * formLogin.successHandler(new AuthenticationSuccessHandler() {
       * 
       * @Override
       * public void onAuthenticationSuccess(HttpServletRequest request,
       * HttpServletResponse response,
       * Authentication authentication) throws IOException, ServletException {
       * // TODO Auto-generated method stub
       * throw new
       * UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'"
       * );
       * }
       * 
       * })
       */
    });
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.logout(logout -> {
      logout.logoutUrl("/logout");
      logout.logoutSuccessUrl("/login?logout=ture");
    });
    // how to add custom login page

    // oauth configuration
    httpSecurity.oauth2Login(oauth -> {
      oauth.loginPage("/login");
      oauth.successHandler(oauth2Success);
    });
    return httpSecurity.build();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();
  }

}
