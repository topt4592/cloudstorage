package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.common.CommonUltis;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersServices;

    @GetMapping("/login")
    public String getViewLogin() {
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/home";
    }

    @GetMapping("/signup")
    public String getViewSignup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(Users users, Model model, RedirectAttributes redirectAttributes) {
        String error = "";
        if (CommonUltis.isValidString(users.getFirstName()) && CommonUltis.isValidString(users.getLastName())
                && CommonUltis.isValidString(users.getPassword()) && CommonUltis.isValidString(users.getUsername())) {
            error = usersServices.validationSignup(users.getUsername());

            if (error.isBlank()) {
                int results = usersServices.addUsers(users);
                if (results == 0) {
                    error = "An error occurred, please try again";
                }
            }
        } else {
            error = "Please input valid First name, Last name, username, password";
        }

        if (!error.isBlank()) {
            model.addAttribute("errorSignup", error);
            return "signup";
        }
        redirectAttributes.addFlashAttribute("signupOK", "OK");
        return "redirect:/login";
    }
}
