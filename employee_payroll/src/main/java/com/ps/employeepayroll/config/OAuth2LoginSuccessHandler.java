package com.ps.employeepayroll.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ps.employeepayroll.model.Employee;
import com.ps.employeepayroll.model.Role;
import com.ps.employeepayroll.repository.EmployeeRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public OAuth2LoginSuccessHandler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // Ensure employeeRepository is not null
        if (employeeRepository == null) {
            throw new IllegalStateException("EmployeeRepository is not initialized.");
        }

        Optional<Employee> existingUser = employeeRepository.findByEmail(email);

        if (existingUser.isEmpty()) {
            Employee newUser = new Employee();
            newUser.setEmail(email);
            newUser.setName(oAuth2User.getAttribute("name"));
            newUser.setRole(Role.USER);
            newUser.setPassword("");

            employeeRepository.save(newUser);
        }

        String redirectUrl = existingUser.isPresent() && existingUser.get().getRole().equals("ADMIN")
                ? "/admin/dashboard"
                : "/user/dashboard";

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
