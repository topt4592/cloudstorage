package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notes {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

    public Notes(String noteTitle, String noteDescription, Integer userId) {
        super();
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userId;
    }
}
