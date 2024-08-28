package SPA.dev.Stock.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Service
public class FileUploadClient {

    private static final String UPLOAD_URL = "http://localhost:8080/api/upload"; // Remplacez par l'URL de votre API

    public String uploadFile(String filePath) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Créer l'en-tête HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Charger le fichier local
            File file = new File(filePath); // Chemin du fichier local
            if (!file.exists()) {
                throw new RuntimeException("Fichier non trouvé : " + file.getAbsolutePath());
            }
            FileSystemResource fileResource = new FileSystemResource(file);

            // Créer l'objet HttpEntity
            HttpEntity<FileSystemResource> requestEntity = new HttpEntity<>(fileResource, headers);

            // Envoyer la requête POST
            ResponseEntity<String> response = restTemplate.postForEntity(UPLOAD_URL, requestEntity, String.class);

            // Retourner la réponse
            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du téléchargement du fichier.");
        }
    }

    // Méthode pour charger une image à partir d'un chemin de fichier
    public FileSystemResource loadImageAsResource(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("Image non trouvée : " + filePath);
        }
        return new FileSystemResource(file);
    }
}
