package com.person.SpringSecurityJWTDemo001.controller;

import com.person.SpringSecurityJWTDemo001.dto.Person;
import com.person.SpringSecurityJWTDemo001.dto.PersonDetailsInput;
import com.person.SpringSecurityJWTDemo001.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gov")
public class GovAuthotity {

    @Autowired
    private PersonService service;

    @PostMapping("/createPerson")
    public String createPerson(@RequestBody PersonDetailsInput detailsInput) {
        return service.createPerson(detailsInput);
    }

    @GetMapping("/getAll")
    public List<Person> getAllPerson() {
        return service.getAllPersons();
    }

    @GetMapping("/getAllByName/{name}")
    public List<Person> getAllPersonByName(@PathVariable String name) {
        return service.getPersonsByName(name);
    }

    @PutMapping("/updateName/{email}/{name}")
    public String updateName(@PathVariable String email, @PathVariable String name) {
        return service.updateName(email, name);
    }

    @PutMapping("/updateGender/{email}/{gender}")
    public String updateGender(@PathVariable String email, @PathVariable String gender) {
        return service.updateGender(email, gender);
    }

    @PutMapping("/updatePhone/{email}/{phone}")
    public String updatePhone(@PathVariable String email, @PathVariable long phone) {
        return service.updatePhone(email, phone);
    }

}
