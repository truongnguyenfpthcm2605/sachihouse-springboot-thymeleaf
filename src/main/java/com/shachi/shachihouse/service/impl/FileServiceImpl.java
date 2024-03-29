package com.shachi.shachihouse.service.impl;

import com.shachi.shachihouse.exception.FileException;
import com.shachi.shachihouse.exception.RuntimeExceptionCustom;
import com.shachi.shachihouse.service.FileService;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final ServletContext app;

    @Override
    public Path getPath(String folder, String filename) {
        File dir = Paths.get(app.getRealPath(folder)).toFile();
        try {
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } catch (Exception e) {
            throw new RuntimeExceptionCustom(e.getMessage());
        }
        return Paths.get(dir.getAbsolutePath(), filename);
    }

    @Override
    public String read(String folder, String filename)  {
        try {
            Path path = this.getPath(folder, filename);
            return path.toFile().getName();
        } catch ( Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(String folder, String filename) {
        Path path = this.getPath(folder, filename);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + filename, e);
        }
    }

    @Override
    public List<String> list(String folder) {
        List<String> filenames = new ArrayList<>();
        Path dir = Paths.get(app.getRealPath(folder));
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                filenames.add(file.getFileName().toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to list files in directory: " + dir, e);
        }
        return filenames;
    }

    @Override
    public List<String> uploadFiles(MultipartFile[] attach, String folderName) {
        List<String> listFileName = new ArrayList<>();
        for (MultipartFile file : attach) {
            String filename = uploadFileString(file,folderName);
            listFileName.add(filename);
        }
        return listFileName;
    }

    @Override
    public String uploadFileString(MultipartFile file, String folderName) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }
            String name = System.currentTimeMillis() + file.getOriginalFilename();
            String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
            Path path = this.getPath(folderName, filename);
            file.transferTo(path);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public File uploadFile(MultipartFile file, String folderName) {
        Path dir = getPath(folderName,file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }
            file.transferTo(dir);
            return dir.toFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file", e);
        }
    }
    @Override
    public String readAndEncodeImage(String folder, String filename) {
        try {
            Path path = this.getPath(folder, filename);
            byte[] imageBytes = Files.readAllBytes(path);
            return "data:image/jpeg;base64,"+encodeImage(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image: " + filename);
        }
    }

    private String encodeImage(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }



}
