package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.FournisseurDto;
import SPA.dev.Stock.service.FournisseurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/fournisseur")
@RequiredArgsConstructor
public class FournisseurController {
    private final FournisseurService fournisseurService;

    @PostMapping
    public ResponseEntity<FournisseurDto> ajouter(@RequestBody FournisseurDto fournisseurDto) {
        return ResponseEntity.ok(fournisseurService.ajouter(fournisseurDto));
    }

    @GetMapping
    public ResponseEntity<List<FournisseurDto>> liste() {
        return ResponseEntity.ok(fournisseurService.liste());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<FournisseurDto>> getApprovisionnement(@PathVariable int id) {
        return ResponseEntity.ok(fournisseurService.getFournisseur(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FournisseurDto> modifier(@PathVariable int id, @RequestBody FournisseurDto fournisseurDto) {
        return ResponseEntity.ok(fournisseurService.modifier(id, fournisseurDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(fournisseurService.delete(id));
    }
}

