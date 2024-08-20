package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.VenteDto;
import SPA.dev.Stock.service.VenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/vente")
public class VenteController {
    private final VenteService venteService;

    public VenteController(VenteService venteService) {
        this.venteService = venteService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<VenteDto>> getVentes(){
        return ResponseEntity.ok(venteService.getAll());
    }
    @GetMapping("/getOne/{id}")
    public ResponseEntity<VenteDto> getVente(@PathVariable Long id){
        return ResponseEntity.ok(venteService.getVente(id));
    }

    @GetMapping("/{produitId}")
    public ResponseEntity<List<VenteDto>> getVentesByProduit(@PathVariable int produitId) {
        return ResponseEntity.ok(venteService.getVenteByProduit(produitId));
    }

    @PostMapping("/add")
    public ResponseEntity<VenteDto> addVente(@Valid @RequestBody VenteDto venteDto){
        return ResponseEntity.ok( venteService.addVente(venteDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<VenteDto> removeVenteById(@PathVariable Long id){
        return ResponseEntity.ok(venteService.removeVente(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VenteDto> updateVente(@PathVariable Long id, @Valid @RequestBody VenteDto venteDto){
        return ResponseEntity.ok(venteService.updateVente(id, venteDto));
    }
}
