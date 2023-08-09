package com.person.SpringSecurityJWTDemo001.repo;

import com.person.SpringSecurityJWTDemo001.dto.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person, Integer> {
    public List<Person> findByName(String name);

    public Person findByEmail(String email);
}
