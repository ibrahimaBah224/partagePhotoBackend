package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.CompteDto;
import SPA.dev.Stock.modele.Compte;
import SPA.dev.Stock.service.CompteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/compte")
public class CompteController {
    private final CompteService compteService;

    @GetMapping("/list")
    public ResponseEntity<List<Compte>> getComptes(){
        return ResponseEntity.ok(compteService.getAll());
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<CompteDto> getCompte(@PathVariable Long id){
        return ResponseEntity.ok(compteService.getCompte(id));
    }

    @PostMapping("/add")
    public ResponseEntity<CompteDto> addCompte(@Valid @RequestBody CompteDto compteDto){
        CompteDto compteDto1 = compteService.addCompte(compteDto);
        return ResponseEntity.created(URI.create("/comptes/"+compteDto1.getId())).body(compteDto1);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CompteDto> removeClient(@PathVariable Long id){
        return ResponseEntity.ok(compteService.removeCompte(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CompteDto> updateClient(@PathVariable Long id, @Valid @RequestBody CompteDto compteDto){
        return ResponseEntity.ok(compteService.updateCompte(id,compteDto));
    }

}
