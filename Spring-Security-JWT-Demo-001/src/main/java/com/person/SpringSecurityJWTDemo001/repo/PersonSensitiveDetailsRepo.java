package com.person.SpringSecurityJWTDemo001.repo;

import com.person.SpringSecurityJWTDemo001.dto.Person;
import com.person.SpringSecurityJWTDemo001.dto.PersonSensitiveDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonSensitiveDetailsRepo extends JpaRepository<PersonSensitiveDetails, Integer> {
}
