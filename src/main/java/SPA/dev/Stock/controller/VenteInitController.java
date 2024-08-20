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
@RequestMapping("/api/vente_init")
public class VenteInitController {
    private final VenteInitService venteInitService;

    @GetMapping("/list")
    public ResponseEntity<List<VenteInitDto>> getVenteInits(){
        return ResponseEntity.ok(venteInitService.getAll());
    }
    @GetMapping("/getOne/{id}")
    public ResponseEntity<VenteInitDto> getVenteInit(@PathVariable Long id){
        return ResponseEntity.ok(venteInitService.getVenteInit(id));
    }

    @PostMapping("/add")
    public ResponseEntity<VenteInitDto> addVenteInit(@Valid @RequestBody VenteInitDto venteInitDto){
        return ResponseEntity.ok( venteInitService.addVenteInit(venteInitDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<VenteInitDto> removeVehicleById(@PathVariable Long id){
        return ResponseEntity.ok(venteInitService.removeVenteInit(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VenteInitDto> updateVenteInit(@PathVariable Long id, @Valid @RequestBody VenteInitDto venteInitDto){
        return ResponseEntity.ok(venteInitService.updateVenteInit(id, venteInitDto));
    }
}
