package SPA.dev.Stock.controller;
import SPA.dev.Stock.dto.ProduitDto;
import SPA.dev.Stock.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/produit")
public class ProduitController {
    private final ProduitService produitService;

    @PostMapping
    public ResponseEntity<ProduitDto> ajouter(@RequestBody ProduitDto produitDto) {
        return ResponseEntity.ok(produitService.ajouter(produitDto));
    }

    @GetMapping
    public ResponseEntity<List<ProduitDto>> liste() {
        return ResponseEntity.ok(produitService.liste());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProduitDto>> getApprovisionnement(@PathVariable int id) {
        return ResponseEntity.ok(produitService.getProduit(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitDto> modifier(@PathVariable int id, @RequestBody ProduitDto produitDto) {
        return ResponseEntity.ok(produitService.modifier(id, produitDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(produitService.delete(id));
    }
}
