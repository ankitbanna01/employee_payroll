package com.ps.employeepayroll.config;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ps.employeepayroll.model.Employee;
import com.ps.employeepayroll.repository.EmployeeRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);

    @Autowired
    private EmployeeRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String provider = oauthToken.getAuthorizedClientRegistrationId();
        logger.info("Provider: " + provider);

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        // Log OAuth2 user attributes
        oauthUser.getAttributes().forEach((key, value) -> logger.info(key + " : " + value));

        // Extract user details
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String profilePic = oauthUser.getAttribute("picture");

        if (email == null) {
            logger.error("OAuth user email is missing!");
            response.sendRedirect("/login?error=oauth-email-missing");
            return;
        }

        // Check if the user already exists
        Employee user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            // Register new user
            user = new Employee();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(""); // No password required for OAuth users
            user.setRole(Employee.Role.USER); // Default role
            userRepository.save(user);
            logger.info("New OAuth user registered: " + email);
        } else {
            // Update existing user details
            user.setName(name);
            userRepository.save(user);
            logger.info("OAuth user updated: " + email);
        }

        // Redirect based on user role
        if (user.getRole() == Employee.Role.ADMIN) {
            new DefaultRedirectStrategy().sendRedirect(request, response, "/admin/dashboard");
        } else {
            new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
        }
    }
}
