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

    @PostMapping("/add")
    public ResponseEntity<TransfertDto> ajouter(@RequestBody TransfertDto transfertDto) {
        return ResponseEntity.ok(transfertService.ajouter(transfertDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<TransfertDto>> liste() {
        return ResponseEntity.ok(transfertService.liste());
    }

    @GetMapping("/listEnCours")
    public ResponseEntity<List<TransfertDto>> getTransfertByMagasin() {
        return ResponseEntity.ok(transfertService.getTransfertByMagasin());
    }

    @GetMapping("getOne/{id}")
    public ResponseEntity<Optional<TransfertDto>> getApprovisionnement(@PathVariable int id) {
        return ResponseEntity.ok(transfertService.getTransfert(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<TransfertDto> modifier(@PathVariable int id, @RequestBody TransfertDto transfertDto) {
        return ResponseEntity.ok(transfertService.modifier(id, transfertDto));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(transfertService.delete(id));
    }
}

