package com.person.SpringSecurityJWTDemo001.dao;

import com.person.SpringSecurityJWTDemo001.dto.Person;
import com.person.SpringSecurityJWTDemo001.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDao {

    @Autowired
    private PersonRepo repo;


    // Creation Operations

    public boolean savePersonDetails(Person person) {
        Person person1 = repo.findByEmail(person.getEmail());
        if (person1 == null) {
            repo.save(person);
            return true;
        }
        return false;
    }


    /**
     * Retrieval Operations
     */

    public Person getPersonByEmail(String email) {
        System.out.println("Test - 3");
        System.out.println(repo.findByEmail(email));
        System.out.println("Test - 4");

        return repo.findByEmail(email);
    }

    public List<Person> getAllPersons() {
        return repo.findAll();
    }

    public List<Person> getAllPersonsByName(String name) {
        return repo.findByName(name);
    }

    /**
     * Updatation Operation
     */

    public boolean updateName(String email, String name) {
        Person person = repo.findByEmail(email);
        person.setName(name);
        repo.save(person);
        person = repo.findByEmail(email);
        return person.getName().equals(name);
    }

    public boolean updatePhone(String email, long phone) {
        Person person = repo.findByEmail(email);
        person.setPhone(phone);
        repo.save(person);
        person = repo.findByEmail(email);
        return person.getPhone() == phone;
    }

    public boolean updateGender(String email, String gender) {
        Person person = repo.findByEmail(email);
        person.setGender(gender);
        repo.save(person);
        person = repo.findByEmail(email);
        return person.getGender().equals(gender);
    }

    public boolean updatePassword(String email, String password) {
        Person person = repo.findByEmail(email);
        person.getSensitiveDetails().setPassword(password);
        repo.save(person);
        person = repo.findByEmail(email);
        return person.getSensitiveDetails().getPassword().equals(password);
    }

    public void updateActiveStatus(String email, int active) {
        Person person = repo.findByEmail(email);
        person.getSensitiveDetails().setActive(active);
        repo.save(person);
    }

    /**
     * Deletion Operations
     */

    public boolean delete(String email) {
        Person person = repo.findByEmail(email);
        repo.delete(person);
        person = repo.findByEmail(email);
        return person == null;
    }


}
