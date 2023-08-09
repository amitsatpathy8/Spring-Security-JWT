package com.person.SpringSecurityJWTDemo001.controller;

import com.person.SpringSecurityJWTDemo001.JWT.JWTService;
import com.person.SpringSecurityJWTDemo001.dto.AuthRequest;
import com.person.SpringSecurityJWTDemo001.dto.Password;
import com.person.SpringSecurityJWTDemo001.dto.Person;
import com.person.SpringSecurityJWTDemo001.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/statusCheck")
    public String status() {
        return "200 success";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated())
            return jwtService.generateToken(authRequest.getEmail());
        return "Invalid Username Or password";
    }

    @GetMapping("/details")
    public Person getOwnDetails(HttpServletRequest request) {
        return service.getPersonDetails(request);
    }

    @PostMapping("/updatePassword")
    public String updatePassword(HttpServletRequest request, @RequestBody Password password) {
        return service.updatePassword(request, password.getPassword());
    }

}
