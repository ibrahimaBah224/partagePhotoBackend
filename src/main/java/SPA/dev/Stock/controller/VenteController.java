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
@RequiredArgsConstructor
public class VenteController {
    private final VenteService venteService;

    @GetMapping("/ventes")
    public ResponseEntity<List<VenteDto>> getVentes(){
        return ResponseEntity.ok(venteService.getAll());
    }
    @GetMapping("/ventes/{id}")
    public ResponseEntity<VenteDto> getVente(@PathVariable Long id){
        return ResponseEntity.ok(venteService.getVente(id));
    }

    @GetMapping("/ventes/produit/{produitId}")
    public ResponseEntity<List<VenteDto>> getVentesByProduit(@PathVariable Long produitId) {
        return ResponseEntity.ok(venteService.getVenteByProduit(produitId));
    }

    @PostMapping("/ventes")
    public ResponseEntity<VenteDto> addVente(@Valid @RequestBody VenteDto venteDto){
        VenteDto createdVente = venteService.addVente(venteDto);
        return ResponseEntity.created(URI.create("/ventes/"+createdVente.getId())).body(createdVente);
    }

    @DeleteMapping("/ventes/{id}")
    public ResponseEntity<VenteDto> removeVenteById(@PathVariable Long id){
        return ResponseEntity.ok(venteService.removeVente(id));
    }

    @PutMapping("/ventes/{id}")
    public ResponseEntity<VenteDto> updateVente(@PathVariable Long id, @Valid @RequestBody VenteDto venteDto){
        return ResponseEntity.ok(venteService.updateVente(id, venteDto));
    }
}
