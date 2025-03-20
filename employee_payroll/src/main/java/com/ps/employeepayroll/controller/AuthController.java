package com.ps.employeepayroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.ps.employeepayroll.model.User;
import com.ps.employeepayroll.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index"; // Render the index page directly
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }

        try {
            userService.registerUser(user);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }

        // Redirect to login page
        return "redirect:/auth/login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin_dashboard";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "user_dashboard";
    }
}