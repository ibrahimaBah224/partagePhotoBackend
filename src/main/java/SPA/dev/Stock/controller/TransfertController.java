package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.TransfertDto;
import SPA.dev.Stock.service.TransfertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/transfert")
@RequiredArgsConstructor
public class TransfertController {
    private final TransfertService transfertService;

    @PostMapping
    public ResponseEntity<TransfertDto> ajouter(@RequestBody TransfertDto transfertDto) {
        return ResponseEntity.ok(transfertService.ajouter(transfertDto));
    }

    @GetMapping
    public ResponseEntity<List<TransfertDto>> liste() {
        return ResponseEntity.ok(transfertService.liste());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TransfertDto>> getApprovisionnement(@PathVariable int id) {
        return ResponseEntity.ok(transfertService.getTransfert(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransfertDto> modifier(@PathVariable int id, @RequestBody TransfertDto transfertDto) {
        return ResponseEntity.ok(transfertService.modifier(id, transfertDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(transfertService.delete(id));
    }
}

