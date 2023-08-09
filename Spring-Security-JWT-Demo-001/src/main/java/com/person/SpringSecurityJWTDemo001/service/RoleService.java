package com.person.SpringSecurityJWTDemo001.service;

import com.person.SpringSecurityJWTDemo001.dao.RolesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleService {
    @Autowired
    private RolesDao dao;

    private String gov = "ROLE_AUTH-GOV";
    private String user = "ROLE_USER";
    private String admin = "ROLE_ADMIN";

    public String makeUser(String email) {
        if (dao.addNewRole(email, user))
            return "User having email : " + email + " now has the authority of User";
        return "Something went wrong";
    }

    public String makeAuthGov(String email) {
        if (dao.addNewRole(email, gov))
            return "User having email : " + email + " now has the authority of AUTH-GOV";
        return "Something went wrong";
    }

    public String makeAdmin(String email) {
        if (dao.addNewRole(email, admin))
            return "User having email : " + email + " now has the authority of ADMIN";
        return "Something went wrong";
    }

    public String removeAdmin(String email) {
        if (dao.deleteRole(email, admin))
            return "User having email : " + email + " now loss the authority of ADMIN";
        return "Something went wrong";
    }

    public String removeAuthGov(String email) {
        if (dao.deleteRole(email, gov))
            return "User having email : " + email + " now loss the authority of AUTH-GOV";
        return "Something went wrong";
    }

}
