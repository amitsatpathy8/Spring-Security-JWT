package com.person.SpringSecurityJWTDemo001.dto;

import lombok.Data;

/* This class is used for getting the email and password from the front end to generate the token */

@Data
public class AuthRequest {
    private String email;
    private String password;
}
