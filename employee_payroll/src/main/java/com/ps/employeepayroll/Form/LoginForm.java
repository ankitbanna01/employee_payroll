package com.ps.employeepayroll.Form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // Generates getters, setters, toString, equals, and hashCode
public class LoginForm {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Role is required")
    private String role;
}
