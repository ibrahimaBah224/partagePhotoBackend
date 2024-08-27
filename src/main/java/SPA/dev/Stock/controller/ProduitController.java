package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.ProduitDto;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/produit")
@CrossOrigin(origins = "http://localhost:6807")
public class ProduitController {
    private final ProduitService produitService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(
            @RequestParam("reference") String reference,
            @RequestParam("designation") String designation,
            @RequestParam("id_sousCategorie") int idSousCategorie,
            @RequestParam("description") String description,
            @RequestParam("seuil") int seuil,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ProduitDto produitDto = new ProduitDto();
        produitDto.setSousCategorie(String.valueOf(idSousCategorie));
        produitDto.setReference(reference);
        produitDto.setDesignation(designation);
        produitDto.setDescription(description);
        produitDto.setSeuil(seuil);
        try {
            return ResponseEntity.ok(produitService.ajouter(produitDto, file));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }



    @GetMapping("/list")
    public ResponseEntity<List<ProduitDto>> liste() {
        return ResponseEntity.ok(produitService.liste());
    }

    @GetMapping("getOne/{id}")
    public ResponseEntity<Optional<ProduitDto>> getProduit(@PathVariable int id) {
        return ResponseEntity.ok(produitService.getProduit(id));
    }

    @PostMapping("/changeStatus/{id}/{statut}")
    public ResponseEntity<Optional<ProduitDto>> changeStatus(@PathVariable int id, @PathVariable int statut){
        return ResponseEntity.ok(Optional.ofNullable(produitService.changeStatut(id, statut)));
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> modifier(
            @PathVariable int id,
            @RequestParam("reference") String reference,
            @RequestParam("designation") String designation,
            @RequestParam("id_sousCategorie") int idSousCategorie,
            @RequestParam("description") String description,
            @RequestParam("seuil") int seuil,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        ProduitDto produitDto=produitService.getProduit(id).orElseThrow(()->new RuntimeException("produit introuvable"));
        produitDto.setSousCategorie(String.valueOf(idSousCategorie));
        produitDto.setReference(reference);
        produitDto.setDesignation(designation);
        produitDto.setDescription(description);
        produitDto.setSeuil(seuil);
        produitDto.setIdProduit(id);
        return ResponseEntity.ok(produitService.modifier(id, produitDto,file));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(produitService.delete(id));
    }
}
