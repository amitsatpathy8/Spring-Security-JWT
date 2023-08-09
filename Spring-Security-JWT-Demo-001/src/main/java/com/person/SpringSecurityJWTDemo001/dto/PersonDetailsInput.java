package com.person.SpringSecurityJWTDemo001.dto;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Date;

/* This Class is used for the converting the user input from front end to create a person in the application */

@Data
public class PersonDetailsInput {
    private String name;
    private String gender;
    private String email;
    private long phone;
    private String password;

    public Person convertInputDetailsIntoPerson() {
        Roles roles = new Roles();
        roles.setEmail(this.email);
        roles.setRole("ROLE_USER");
        PersonSensitiveDetails sensitiveDetails = new PersonSensitiveDetails();
        sensitiveDetails.setActive(1);
        sensitiveDetails.setPassword(new BCryptPasswordEncoder().encode(this.password));
        sensitiveDetails.setDateOfJoining(new Date());
        Person person = new Person();
        person.setName(this.name);
        person.setPhone(this.phone);
        person.setEmail(this.email);
        person.setGender(this.gender);
        person.setSensitiveDetails(sensitiveDetails);
        person.setRoles(Arrays.asList(roles));
        return person;
    }
}
