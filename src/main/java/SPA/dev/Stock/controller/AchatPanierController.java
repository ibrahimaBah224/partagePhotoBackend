package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.AchatInitDto;
import SPA.dev.Stock.dto.AchatPanierDto;
import SPA.dev.Stock.service.AchatInitService;
import SPA.dev.Stock.service.AchatPanierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/achatPanier")
public class AchatPanierController {
    private final AchatPanierService achatPanierService;

    @GetMapping("/list")
    public ResponseEntity<List<AchatPanierDto>> getAchatInits(){
        return ResponseEntity.ok(achatPanierService.getAll());
    }
    @GetMapping("/getOne/{id}")
    public ResponseEntity<AchatPanierDto> getAchatInit(@PathVariable Long id){
        return ResponseEntity.ok(achatPanierService.getAchat(id));
    }



    @PostMapping("/add")
    public ResponseEntity<AchatPanierDto> addAchatInit(@Valid @RequestBody AchatPanierDto achatInitDto){
        return ResponseEntity.ok( achatPanierService.addAchat(achatInitDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AchatPanierDto> removeAchat(@PathVariable Long id){
        return ResponseEntity.ok(achatPanierService.removeAchat(id));
    }

    @GetMapping("/achatEnCours/{id}")
    public ResponseEntity<Object[]> achatEnCours(@PathVariable int id){
        return ResponseEntity.ok(achatPanierService.achatEnCours(id));
    }
}
