package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.service.MagasinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/magasin")
@RequiredArgsConstructor
public class MagasinController {
    private final MagasinService magasinService;

    @PostMapping
    public ResponseEntity<MagasinDto> ajouter(@RequestBody MagasinDto magasinDto) {
        return ResponseEntity.ok(magasinService.ajouter(magasinDto));
    }

    @GetMapping
    public ResponseEntity<List<MagasinDto>> liste() {
        return ResponseEntity.ok(magasinService.liste());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MagasinDto>> getApprovisionnement(@PathVariable int id) {
        return ResponseEntity.ok(magasinService.getMagasin(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MagasinDto> modifier(@PathVariable int id, @RequestBody MagasinDto magasinDto) {
        return ResponseEntity.ok(magasinService.modifier(id, magasinDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(magasinService.delete(id));
    }
}
