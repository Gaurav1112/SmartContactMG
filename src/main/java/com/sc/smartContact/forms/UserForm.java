package com.sc.smartContact.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

  @NotBlank(message = "userName is Required")
  @Size(min = 3, message = "Min 3 characters required")
  private String name;
  @NotBlank(message = "Email is Required")
  @Email(message = "Invalid Email Id")
  private String email;
  @NotBlank(message = "password cant be blank")
  @Size(min = 6, message = "Min 6 characters required")
  private String password;
  private String about;
  @Size(min = 8, max = 12, message = "Invalid Phone Number")
  private String phoneNumber;

}
