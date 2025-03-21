package com.ps.employeepayroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ps.employeepayroll.model.Employee;
import com.ps.employeepayroll.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    // ✅ Show Login Page
    @GetMapping("/auth/login")
    public String loginPage(@RequestParam(required = false) String role, Model model) {
        model.addAttribute("role", (role != null) ? role : "USER");
        model.addAttribute("loginForm", new Employee());
        return "login";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // After login, check the user's role
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // Redirect to admin dashboard
            return "redirect:/admin/dashboard";
        } else {
            // Redirect to user dashboard
            return "redirect:/user/dashboard";
        }
    }

    @GetMapping("/login/oauth2/code/google")
    public String googleCallback(@AuthenticationPrincipal OAuth2User oauthUser, HttpSession session) {
        if (oauthUser == null) {
            return "redirect:/auth/login?error=oauth";
        }

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        if (email == null || name == null) {
            return "redirect:/auth/login?error=missing_info";
        }

        // Debugging: Print received Google user info (Remove in production)
        System.out.println("Google Login - Email: " + email + ", Name: " + name);

        // Find or create user (with role assignment)
        Employee user = employeeService.findOrCreateGoogleUser(email, name);

        if (user == null) {
            return "redirect:/auth/login?error=user_not_found";
        }

        session.setAttribute("loggedUser", user);

        // Ensure role check works correctly
        String role = user.getRole() != null ? user.getRole().toString() : "";
        return role.equals("ADMIN") ? "redirect:/admin/dashboard" : "redirect:/user/dashboard";
    }

    // ✅ Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login?logout";
    }
}
