package com.ps.employeepayroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ps.employeepayroll.Form.LoginForm;
import com.ps.employeepayroll.model.User;
import com.ps.employeepayroll.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/auth/login")
    public String loginPage(@RequestParam(required = false) String role, Model model) {
        model.addAttribute("role", role); // Pass role to the login page
        model.addAttribute("loginForm", new LoginForm()); // Add LoginForm to the model
        return "login"; // Render the login page
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        try {
            User user = userService.authenticateUser(email, password);

            // ✅ Store user info in session
            session.setAttribute("loggedUser", user);

            // ✅ Redirect based on role
            if (user.getRole() == User.Role.ADMIN) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/dashboard";
            }

        } catch (Exception e) {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    // ✅ Google OAuth2 Login Handling
    @GetMapping("/oauth2/callback")
    public String googleLogin(@AuthenticationPrincipal OAuth2User oauthUser, HttpSession session) {
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        User user = userService.findOrCreateGoogleUser(email, name);

        // ✅ Store user info in session
        session.setAttribute("loggedUser", user);

        // ✅ Redirect based on role
        if (user.getRole() == User.Role.ADMIN) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/user/dashboard";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login?logout";
    }
}