package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.ClientDto;
import SPA.dev.Stock.dto.PerteDto;
import SPA.dev.Stock.dto.RecyclageDto;
import SPA.dev.Stock.modele.Perte;
import SPA.dev.Stock.service.PerteService;
import SPA.dev.Stock.service.RecyclageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/perte")
@RequiredArgsConstructor
public class PerteController {
    private final PerteService perteService;

    @GetMapping("/list")
    public ResponseEntity<List<PerteDto>> getClients(){

        return ResponseEntity.ok(perteService.getAll());
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<PerteDto> getPerte(@PathVariable Long id){
        return ResponseEntity.ok(perteService.getPerte(id));
    }

    @PostMapping("/add")
    public ResponseEntity<PerteDto> addClient(@Valid @RequestBody PerteDto perteDto){
        return ResponseEntity.ok(perteService.addPerte(perteDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PerteDto> removeClient(@PathVariable Long id){
        return ResponseEntity.ok(perteService.removePerte(id));
    }



}
