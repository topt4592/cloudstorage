package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.common.CommonUltis;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;

@Service
public class UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private HashService hashService;

    public Users getByUsername(String username) {
        return usersMapper.findByUsername(username);
    }

    public int addUsers(Users users) {

        /* ramdom salt */
        String encodeSaltbase64 = CommonUltis.ramdomEncodeBase64();
        String passHashed = hashService.getHashedValue(users.getPassword(), encodeSaltbase64);
        return usersMapper.addUser(new Users(null, users.getUsername(), encodeSaltbase64, passHashed,
                users.getFirstName(), users.getLastName()));
    }

    public String validationSignup(String username) {
        String error = "";
        if (username == null || username.isBlank() || username.isEmpty()) {
            error = "Username can not null, blank, emty";
        } else if (getByUsername(username) != null) {
            error = "Username already exists, please enter another user";
        }
        return error;
    }
}
