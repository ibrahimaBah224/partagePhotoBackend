package SPA.dev.Stock.controller;
import SPA.dev.Stock.dto.CategorieDto;
import SPA.dev.Stock.service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/categorie")
@RequiredArgsConstructor
public class CategorieController {
    private final CategorieService categorieService;

    @PostMapping("/add")
    public ResponseEntity<CategorieDto> ajouter(@RequestBody CategorieDto categorieDto) {
        return ResponseEntity.ok(categorieService.ajouter(categorieDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategorieDto>> liste() {
        return ResponseEntity.ok(categorieService.liste());
    }

    @GetMapping("getOne/{id}")
    public ResponseEntity<Optional<CategorieDto>> getApprovisionnement(@PathVariable int id) {
        return ResponseEntity.ok(categorieService.getCategorie(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<CategorieDto> modifier(@PathVariable int id, @RequestBody CategorieDto categorieDto) {
        return ResponseEntity.ok(categorieService.modifier(id, categorieDto));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(categorieService.delete(id));
    }
}
