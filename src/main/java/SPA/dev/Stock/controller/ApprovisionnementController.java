package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.ApprovisionnementDto;
import SPA.dev.Stock.service.ApprovisionnementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/approvisionnement")
@RequiredArgsConstructor
public class ApprovisionnementController {
    private final ApprovisionnementService approvisionnementService;

    @PostMapping("/add")
    public ResponseEntity<ApprovisionnementDto> ajouter(@RequestBody ApprovisionnementDto approvisionnementDTO) {
        return ResponseEntity.ok(approvisionnementService.ajouter(approvisionnementDTO));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ApprovisionnementDto>> liste() {
        return ResponseEntity.ok(approvisionnementService.liste());
    }

    @GetMapping("getOne/{id}")
    public ResponseEntity<Optional<ApprovisionnementDto>> getApprovisionnement(@PathVariable int id) {
        return ResponseEntity.ok(approvisionnementService.getApprovisionnement(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApprovisionnementDto> modifier(@PathVariable int id, @RequestBody ApprovisionnementDto approvisionnementDTO) {
        return ResponseEntity.ok(approvisionnementService.modifier(id, approvisionnementDTO));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(approvisionnementService.delete(id));
    }

}
