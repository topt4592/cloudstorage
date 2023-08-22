package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.entity.FilesUpload;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesUploadMapper;

@Service
public class FilesUploadService {

    @Autowired
    FilesUploadMapper filesSUploadMapper;

    public List<FilesUpload> findAllFiles(Integer userId) {
        return filesSUploadMapper.findAllFiles(userId);
    }

    public int addFiles(MultipartFile file, Integer userId) throws IOException {
        FilesUpload filesUpload = new FilesUpload();
        filesUpload.setFileName(getPartFiles(file));
        filesUpload.setContentType(file.getContentType());
        filesUpload.setFileSize(String.valueOf(file.getSize()));
        filesUpload.setFileData(file.getBytes());
        filesUpload.setUserId(userId);
        return filesSUploadMapper.addFiles(filesUpload);
    }

    public int deleteFileById(Integer fileId, Integer userId) {
        return filesSUploadMapper.deleteById(fileId, userId);
    }

    public FilesUpload findOneFile(MultipartFile files, Integer userId) {
        return filesSUploadMapper.findOneFile(getPartFiles(files), userId);
    }
    
    public FilesUpload getFileById(Integer fileId, Integer userId) {
        return filesSUploadMapper.findByUserId(fileId, userId);
    }

    private String getPartFiles(MultipartFile files) {
        String fileName = files.getOriginalFilename();
        int startIndex = fileName.replaceAll("\\\\", "/").lastIndexOf("/");
        return fileName.substring(startIndex + 1);
    }
}
