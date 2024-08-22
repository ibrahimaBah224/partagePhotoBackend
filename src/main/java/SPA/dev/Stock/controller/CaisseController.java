package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.CaisseDto;
import SPA.dev.Stock.dto.ClientDto;
import SPA.dev.Stock.service.CaisseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caisse")
@RequiredArgsConstructor
public class CaisseController {
    private  final CaisseService caisseService;

    @GetMapping("/list")
    public ResponseEntity<List<CaisseDto>> getCaisses(){
        return ResponseEntity.ok(caisseService.getAll());
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<CaisseDto> getCaisse(@PathVariable Long id){
        return ResponseEntity.ok(caisseService.getCaisse(id));
    }

    @PostMapping("/add")
    public ResponseEntity<CaisseDto> addCaisse(@Valid @RequestBody CaisseDto caisseDto){

        return ResponseEntity.ok(caisseService.addCaisse(caisseDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CaisseDto> removeCaisse(@PathVariable Long id){
        return ResponseEntity.ok(caisseService.removeCaisse(id));
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<CaisseDto> updateCaisse(@PathVariable Long id, @Valid @RequestBody CaisseDto caisseDto){
        return ResponseEntity.ok(caisseService.updateCaisse(id,caisseDto));
    }

}
