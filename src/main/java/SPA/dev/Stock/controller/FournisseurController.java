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

    @PostMapping("/add")
    public ResponseEntity<FournisseurDto> ajouter(@RequestBody FournisseurDto fournisseurDto) {
        return ResponseEntity.ok(fournisseurService.ajouter(fournisseurDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<FournisseurDto>> liste() {
        return ResponseEntity.ok(fournisseurService.liste());
    }

    @GetMapping("getOne/{id}")
    public ResponseEntity<Optional<FournisseurDto>> getApprovisionnement(@PathVariable int id) {
        return ResponseEntity.ok(fournisseurService.getFournisseur(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<FournisseurDto> modifier(@PathVariable int id, @RequestBody FournisseurDto fournisseurDto) {
        return ResponseEntity.ok(fournisseurService.modifier(id, fournisseurDto));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(fournisseurService.delete(id));
    }
}

