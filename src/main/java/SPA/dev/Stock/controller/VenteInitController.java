package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.VenteInitDto;
import SPA.dev.Stock.service.VenteInitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VenteInitController {
    private final VenteInitService venteInitService;

    @GetMapping("/vente_init")
    public ResponseEntity<List<VenteInitDto>> getVenteInits(){
        return ResponseEntity.ok(venteInitService.getAll());
    }
    @GetMapping("/vente_init/{id}")
    public ResponseEntity<VenteInitDto> getVenteInit(@PathVariable Long id){
        return ResponseEntity.ok(venteInitService.getVenteInit(id));
    }

    @PostMapping("/vente_init")
    public ResponseEntity<VenteInitDto> addVenteInit(@Valid @RequestBody VenteInitDto venteInitDto){
        VenteInitDto createdVenteInit = venteInitService.addVenteInit(venteInitDto);

        return ResponseEntity.created(URI.create("/vente_init/"+createdVenteInit.getId())).body(createdVenteInit);
    }

    @DeleteMapping("/vente_init/{id}")
    public ResponseEntity<VenteInitDto> removeVehicleById(@PathVariable Long id){
        return ResponseEntity.ok(venteInitService.removeVenteInit(id));
    }

    @PutMapping("/vente_init/{id}")
    public ResponseEntity<VenteInitDto> updateVenteInit(@PathVariable Long id, @Valid @RequestBody VenteInitDto venteInitDto){
        return ResponseEntity.ok(venteInitService.updateVenteInit(id, venteInitDto));
    }
}
