package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.common.CommonUltis;
import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;

@Service
public class CredentialsService {

    @Autowired
    private CredentialsMapper credentialsMapper;

    @Autowired
    private EncryptionService encryptionService;

    public List<Credentials> getAllListCredentials(Integer userId) {
        return credentialsMapper.getAllListCredentials(userId);
    }

    public Credentials getCredentialById(Integer credentialId, Integer userId) {
        return credentialsMapper.getCredentialById(credentialId, userId);
    }

    public int addCredentials(Credentials credential) {
        encryptPassword(credential);
        return credentialsMapper.addCredentials(credential);
    }

    public int deleteCreById(Integer creId, Integer userId) {
        return credentialsMapper.deleteCreById(creId, userId);
    }

    public int updateCreById(Credentials credential) {
        encryptPassword(credential);
        return credentialsMapper.updateCreById(credential);
    }

    public void encryptPassword(Credentials credentials) {
        String encodedKey = CommonUltis.ramdomEncodeBase64();
        credentials.setPassword(encryptionService.encryptValue(credentials.getPassword(), encodedKey));
        credentials.setKey(encodedKey);
    }

    public Credentials decryptPassword(Credentials credential) {
        if (credential.getKey() == null) {
            return credential;
        }
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }

}
