package com.person.SpringSecurityJWTDemo001.controller;

import com.person.SpringSecurityJWTDemo001.dao.PersonDao;
import com.person.SpringSecurityJWTDemo001.service.PersonService;
import com.person.SpringSecurityJWTDemo001.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private PersonService service;

    @Autowired
    private RoleService roleService;

    @DeleteMapping("/delete/{email}")
    public String deletePerson(@PathVariable String email) {
        return service.deletePerson(email);
    }

    @PutMapping("/makeUser/{email}")
    public String makeUser(@PathVariable String email){
        return roleService.makeUser(email);
    }

    @PutMapping("/makeAuth-Gov/{email}")
    public String makeAuthGov(@PathVariable String email){
        return roleService.makeAuthGov(email);
    }

    @PutMapping("/makeAdmin/{email}")
    public String makeAdmin(@PathVariable String email){
        return roleService.makeAdmin(email);
    }

    @DeleteMapping("/removeAdmin/{email}")
    public String removeAdmin(@PathVariable String email){
        return roleService.removeAdmin(email);
    }

    @DeleteMapping("/removeAuth-Gov/{email}")
    public String removeAuthGov(@PathVariable String email){
        return roleService.removeAuthGov(email);
    }


}
