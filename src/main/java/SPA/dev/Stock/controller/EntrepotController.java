package SPA.dev.Stock.controller;
import SPA.dev.Stock.dto.EntrepotDto;
import SPA.dev.Stock.service.EntrepotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/entrepot")
@RequiredArgsConstructor
public class EntrepotController {
    private final EntrepotService entrepotService;

    @PostMapping("/add")
    public ResponseEntity<EntrepotDto> ajouter(@RequestBody EntrepotDto entrepotDto) {
        return ResponseEntity.ok(entrepotService.ajouter(entrepotDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<EntrepotDto>> liste() {
        return ResponseEntity.ok(entrepotService.liste());
    }

    @GetMapping("getOne/{id}")
    public ResponseEntity<Optional<EntrepotDto>> getApprovisionnement(@PathVariable int id) {
        return ResponseEntity.ok(entrepotService.getEntrepot(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<EntrepotDto> modifier(@PathVariable int id, @RequestBody EntrepotDto entrepotDto) {
        return ResponseEntity.ok(entrepotService.modifier(id, entrepotDto));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(entrepotService.delete(id));
    }
}

