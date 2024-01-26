package com.poly.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Service
public class FileManagerService {

	@Autowired
	ServletContext app;

	private Path getPath(String folder, String filename) {
		File dir = Paths.get(app.getRealPath("/files/"), folder).toFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return Paths.get(dir.getAbsolutePath(), filename);
	}

	public byte[] read(String folder, String name) {
		try {
			Path path = this.getPath(folder, name);
			return Files.readAllBytes(path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(String folder, String file) {
		Path path = this.getPath(folder, file);
		path.toFile().delete();
	}

	public List<String> list(String folder) {
		List<String> filenames = new ArrayList<>();
		File dir = Paths.get(app.getRealPath("/files/"), folder).toFile();
		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				filenames.add(file.getName());
			}

		}
		return filenames;

	}

	public List<String> getListFileName(MultipartFile[] attach, String folderName)
			throws IllegalStateException, IOException {
		List<String> listFileName = new ArrayList<>();
		for (MultipartFile file : attach) {
			String name = System.currentTimeMillis() + file.getOriginalFilename();
			String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
			Path path = this.getPath(folderName, filename);
			try {
				file.transferTo(path);
				listFileName.add(filename);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return listFileName;

	}


}
