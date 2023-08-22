package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;

@Controller
public class CredentialsController {

    @Autowired
    private CredentialsService credentialsServices;

    @GetMapping("/showCredentials")
    @ResponseBody
    public Credentials showCredentials(@RequestParam("credentialId") Integer credentialId, Authentication authentication) {
        Users users = (Users) authentication.getPrincipal();
        Credentials credentials = null;
        credentials = credentialsServices.getCreById(credentialId, users.getUserId());

        if (credentials == null) {
            return null;
        }
        credentials = credentialsServices.decryptPassword(credentials);
        return credentials;
    }

    @PostMapping("/saveCredentials")
    public String saveCredentials(Credentials credential, Authentication authentication, Model model) {
    	Integer credentialId = credential.getCredentialId();
        Users users = (Users) authentication.getPrincipal();
        credential.setUserId(users.getUserId());

        int cnt = 0;

        // Check exist Credentials
        // Exist: Update - Not exist: Add
        if (credentialId == null) {
            cnt = credentialsServices.addCredentials(credential);
        } else {
            if (credentialsServices.getCreById(credentialId, users.getUserId()) != null) {
                cnt = credentialsServices.editCreById(credential);
            }
        }

        if (cnt == 0) {
            model.addAttribute("errorSaveFiles", "Save Credential error.");
            return "result";
        }
        model.addAttribute("resultOK", "Save Credential successfully.");
        return "result";
    }

    @GetMapping("/deleteCredentials")
    public String deleteCredentials(@RequestParam("credentialId") Integer credentialId, Authentication authentication, Model model) {
        Users users = (Users) authentication.getPrincipal();
        if (credentialId != null) {
            int cnt = credentialsServices.deleteCreById(credentialId, users.getUserId());
            if (cnt == 0) {
                model.addAttribute("errorSaveFiles", "Detele Credentials error.");
                return "result";
            }
            model.addAttribute("resultOK", "Delete Credentials successfully.");
            return "result";
        }
        return "error";
    }
}
