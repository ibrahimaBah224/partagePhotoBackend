package SPA.dev.Stock.controller;


import SPA.dev.Stock.dto.AchatInitDto;
import SPA.dev.Stock.dto.VenteInitDto;
import SPA.dev.Stock.service.AchatInitService;
import SPA.dev.Stock.service.VenteInitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/achatInit")
public class AchatInitController {
    private final AchatInitService achatInitService;

    @GetMapping("/list")
    public ResponseEntity<List<AchatInitDto>> getAchatInits(){
        return ResponseEntity.ok(achatInitService.getAll());
    }
    @GetMapping("/getOne/{id}")
    public ResponseEntity<AchatInitDto> getAchatInit(@PathVariable Long id){
        return ResponseEntity.ok(achatInitService.getAchatInit(id));
    }

    @GetMapping("/getLastInitVente")
    public ResponseEntity<AchatInitDto> getAchatInit(){
        return ResponseEntity.ok(achatInitService.getLastInitAchat());
    }

    @PostMapping("/add")
    public ResponseEntity<AchatInitDto> addAchatInit(@Valid @RequestBody AchatInitDto achatInitDto){
        return ResponseEntity.ok( achatInitService.addAchatInit(achatInitDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AchatInitDto> removeAchatInit(@PathVariable Long id){
        return ResponseEntity.ok(achatInitService.removeAchatInit(id));
    }


    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<AchatInitDto> updateStatus(@PathVariable long id,@RequestBody int status){
        return  ResponseEntity.ok(achatInitService.updateStatutAchatInit(id,status));
    }
}
