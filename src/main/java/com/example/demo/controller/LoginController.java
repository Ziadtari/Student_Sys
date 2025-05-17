package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;


@Controller
public class LoginController {

    @GetMapping("/loginform")
    public String showLoginForm(Authentication authentication) {
        return "login";
    }

    @GetMapping("/signup")
    public String openSignupForm() {
        return "signup"; // Show signup page
    }
}