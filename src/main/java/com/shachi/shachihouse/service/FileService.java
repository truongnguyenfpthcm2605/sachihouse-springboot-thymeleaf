package com.shachi.shachihouse.service;

import com.shachi.shachihouse.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface FileService {
     Path getPath(String folder, String filename) ;

     String read(String folder, String name) throws FileException;

     void delete(String folder, String file) ;
    
     List<String> list(String folder) ;
    
     List<String> uploadFiles(MultipartFile[] attach, String folderName);

     String uploadFileString(MultipartFile file, String folderName);
     File uploadFile(MultipartFile file, String folderName);
     public String readAndEncodeImage(String folder, String filename);
           

    
}
