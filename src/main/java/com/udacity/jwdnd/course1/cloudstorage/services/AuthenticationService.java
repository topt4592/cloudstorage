package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService implements AuthenticationProvider {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private HashService hashService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Users users = usersMapper.findByUsername(username);
        if (users != null) {
            String salt = users.getSalt();
            String hashedPassword = hashService.getHashedValue(password, salt);
            if (users.getPassword().equals(hashedPassword)) {
                return new UsernamePasswordAuthenticationToken(users, password, new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
