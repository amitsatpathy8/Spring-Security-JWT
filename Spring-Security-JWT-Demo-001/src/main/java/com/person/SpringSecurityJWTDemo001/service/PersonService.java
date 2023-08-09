package com.person.SpringSecurityJWTDemo001.service;

import com.person.SpringSecurityJWTDemo001.JWT.JWTService;
import com.person.SpringSecurityJWTDemo001.dao.PersonDao;
import com.person.SpringSecurityJWTDemo001.dto.Person;
import com.person.SpringSecurityJWTDemo001.dto.PersonDetailsInput;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonService {
    @Autowired
    private PersonDao personDao;

    @Autowired
    private JWTService jwtService;

    public String createPerson(PersonDetailsInput detailsInput) {
        Person person = detailsInput.convertInputDetailsIntoPerson();
        if (personDao.savePersonDetails(person))
            return "New User Created.   Name: " + detailsInput.getName() + " : email : " + detailsInput.getEmail();
        else
            return "Something went wrong";
    }

    public Person getPersonDetails(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        return personDao.getPersonByEmail(email);
    }

    public List<Person> getAllPersons() {
        return personDao.getAllPersons();
    }

    public List<Person> getPersonsByName(String name) {
        return personDao.getAllPersonsByName(name);
    }

    public String updateName(String email, String name) {
        return personDao.updateName(email, name) ? "Name Update Successful" : "Something went Wrong";
    }

    public String updateGender(String email, String gender) {
        return personDao.updateGender(email, gender) ? "Gender Update Successful" : "Something went Wrong";
    }

    public String updatePhone(String email, long phone) {
        return personDao.updatePhone(email, phone) ? "Phone Number Update Successful" : "Something went Wrong";
    }

    public String updatePassword(HttpServletRequest request, String password) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        password = new BCryptPasswordEncoder().encode(password);
        return personDao.updatePassword(email, password) ? "Password update Successful" : "Something went wrong";
    }

    public String deletePerson(String email) {
        Person person = personDao.getPersonByEmail(email);
        if (person == null)
            return "No person found with email: " + email;

        personDao.delete(email);
        person = personDao.getPersonByEmail(email);
        if (person == null)
            return "Delete successful";
        return "Something went wrong";
    }


}
