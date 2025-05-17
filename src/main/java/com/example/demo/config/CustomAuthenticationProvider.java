package com.example.demo.config;

import com.example.demo.service.UserService;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    public Authentication authenticate(Authentication authentication) {
        String email = authentication.getName(); // get the email from the Authentication
        String password = (String) authentication.getCredentials(); // get the password from Authentication

        // Use your custom authenticate method from UserService
        // Directly get the user, no need for Optional variable or isPresent()/get() methods
        User user = userService.authenticate(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        // Now, 'user' is a valid User object, no need to call isPresent() or get()
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority(user.getRole()))
                .build();

        // Create the authentication token
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, user.getPassword(), userDetails.getAuthorities());

        // Store the authentication in the context
        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}