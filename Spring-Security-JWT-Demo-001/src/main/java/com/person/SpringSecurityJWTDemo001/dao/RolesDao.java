package com.person.SpringSecurityJWTDemo001.dao;

import com.person.SpringSecurityJWTDemo001.dto.Person;
import com.person.SpringSecurityJWTDemo001.dto.Roles;
import com.person.SpringSecurityJWTDemo001.repo.PersonRepo;
import com.person.SpringSecurityJWTDemo001.repo.RolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RolesDao {
    @Autowired
    private PersonRepo personRepo;
    @Autowired
    private RolesRepo repo;

    public List<Roles> getRolesByEmail(String email) {
        return repo.findByEmail(email);
    }

    /**
     * Updation Operations
     */

    public boolean addNewRole(String email, String role) {
        List<Roles> rolesList = repo.findByEmail(email);
        boolean isPresent = false;
        for (Roles roles : rolesList)
            if (roles.getRole().equals(role)) {
                isPresent = true;
                break;
            }
        if (!isPresent) {
            Roles roles = new Roles();
            roles.setEmail(email);
            roles.setRole(role);
            Person person = personRepo.findByEmail(email);
            person.getRoles().add(roles);
            personRepo.save(person);
            return true;
        }
        return false;
    }

    /**
     * Deletions Operations
     */

    public boolean deleteRole(String email, String role) {
        List<Roles> roles = repo.findByEmail(email);
        boolean ispresent = false;
        for (Roles roles1 : roles)
            if (roles1.getRole().equals(role))
                ispresent = true;
        if (ispresent) {
            Person person = personRepo.findByEmail(email);
            for (Roles roles2 : person.getRoles()) {
                if (roles2.getRole().equals(role)) {
                    person.getRoles().remove(roles2);
                    personRepo.save(person);
                    repo.delete(roles2);
                    break;
                }
            }
            System.out.println(personRepo.findByEmail(email));
            return true;
        }
        return false;
    }
}
