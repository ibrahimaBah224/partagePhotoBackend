package SPA.dev.Stock.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/photo")
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "pdf", "docx");

    @PostMapping("api/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
            if (!allowedExtensions.contains(fileExtension)) {
                return ResponseEntity.badRequest().body("Extension de fichier non autorisée.");
            }

            Path filePath = Paths.get(uploadDir + "/" + originalFileName);
            Files.copy(file.getInputStream(), filePath);

            String fileLocation = filePath.toAbsolutePath().toString();
            return ResponseEntity.ok(fileLocation);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erreur lors du téléchargement du fichier : " + e.getMessage());
        }
    }
}

