package com.person.SpringSecurityJWTDemo001.SecurityConfig;

import com.person.SpringSecurityJWTDemo001.dao.PersonDao;
import com.person.SpringSecurityJWTDemo001.dto.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PersonIntoUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonDao personDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Test - 2");
        Person person = personDao.getPersonByEmail(username);
        System.out.println(person);
        if (person != null)
            return new PersonIntoUserDetails(person);
        throw new UsernameNotFoundException("Person not found with email : " + username);
    }
}
