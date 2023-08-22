package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import com.udacity.jwdnd.course1.cloudstorage.entity.FilesUpload;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesUploadService;

@Controller
public class FilesUploadController {

    @Autowired
    private FilesUploadService filesUploadServices;

    @GetMapping("/viewFile")
    public ResponseEntity<byte[]> handleViewFile(@RequestParam("fileId")Integer fileId, Authentication authentication) {
        Users users = (Users) authentication.getPrincipal();
        FilesUpload file = filesUploadServices.getFileById(fileId, users.getUserId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", file.getFileName());
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        return new ResponseEntity<>(file.getFileData(), headers, HttpStatus.OK);
    }

    @PostMapping("/saveFile")
    public String saveFile(Authentication authentication, MultipartFile fileUpload, Model modal) throws IOException {
        Users users = (Users) authentication.getPrincipal();
        String errorFilesUpload = "";

        if (fileUpload.isEmpty()) {
            errorFilesUpload = "File upload is empty";
        }

        FilesUpload filesExits = filesUploadServices.findOneFile(fileUpload, users.getUserId());
        if (filesExits != null) {
            errorFilesUpload = " File has been exits.";
        }

        if (!errorFilesUpload.isBlank()) {
            modal.addAttribute("errorSaveFiles", errorFilesUpload);
            return "result";
        }

        int saveFile = filesUploadServices.addFiles(fileUpload, users.getUserId());
        if (saveFile == 0) {
            modal.addAttribute("errorSaveFiles", "Upload failed, Upload file again");
            return "result";
        }

        modal.addAttribute("resultOK", "Upload file successfully");
        return "result";
    }

    @GetMapping("/deleteFile")
    public String deleteFile(@RequestParam("fileId") Integer fileid, Authentication authentication, Model modal) {
        Users users = (Users) authentication.getPrincipal();
        if (fileid != null) {
            int results = filesUploadServices.deleteFileById(fileid, users.getUserId());
            if (results == 0) {
                modal.addAttribute("errorSaveFiles", "File Not exist file or error");
                return "result";
            }
            modal.addAttribute("resultOK", "Delete file successfully");
            return "result";
        }
        return "error";
    }
}
