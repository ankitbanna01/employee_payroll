package com.ps.employeepayroll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("user")
public class EmployeeController {

    @GetMapping("/dashboard")
    public String getDashboard() {
        return "user_dashboard";
    }

}
