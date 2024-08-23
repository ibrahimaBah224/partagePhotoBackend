package SPA.dev.Stock.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) throws IOException {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(uploadDir, originalFileName);
        Files.copy(file.getInputStream(), filePath);
        return filePath.toUri().toString();
    }
}
