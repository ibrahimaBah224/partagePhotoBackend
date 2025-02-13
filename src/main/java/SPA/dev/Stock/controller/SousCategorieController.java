package SPA.dev.Stock.controller;
import SPA.dev.Stock.dto.SousCategorieDto;
import SPA.dev.Stock.service.SousCategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/sousCategorie")
@RequiredArgsConstructor
public class SousCategorieController {
    private final SousCategorieService sousCategorieService;

    @PostMapping("/add")
    public ResponseEntity<SousCategorieDto> ajouter(@RequestBody SousCategorieDto sousCategorieDto) {
        return ResponseEntity.ok(sousCategorieService.ajouter(sousCategorieDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<SousCategorieDto>> liste() {
        return ResponseEntity.ok(sousCategorieService.liste());
    }

    @GetMapping("getOne/{id}")
    public ResponseEntity<Optional<SousCategorieDto>> getApprovisionnement(@PathVariable int id) {
        return ResponseEntity.ok(sousCategorieService.getSousCategorie(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<SousCategorieDto> modifier(@PathVariable int id, @RequestBody SousCategorieDto sousCategorieDto) {
        return ResponseEntity.ok(sousCategorieService.modifier(id, sousCategorieDto));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(sousCategorieService.delete(id));
    }
}

