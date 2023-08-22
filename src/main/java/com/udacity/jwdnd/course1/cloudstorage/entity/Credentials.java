package com.udacity.jwdnd.course1.cloudstorage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    private Integer credentialId;
    private String url;
    private String username;
    @JsonIgnore
    private String key;
    private String password;
    private Integer userId;
}
