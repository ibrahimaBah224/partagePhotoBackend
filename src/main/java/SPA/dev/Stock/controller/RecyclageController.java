package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.PerteDto;
import SPA.dev.Stock.dto.RecyclageDto;
import SPA.dev.Stock.service.RecyclageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecyclageController {
    private final RecyclageService recyclageService;

    @GetMapping("/list")
    public ResponseEntity<List<RecyclageDto>> getClients(){
        return ResponseEntity.ok(recyclageService.getAll());
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<RecyclageDto> getRecyclage(@PathVariable Long id){
        return ResponseEntity.ok(recyclageService.getPerte(id));
    }

    @PostMapping("/add")
    public ResponseEntity<RecyclageDto> addRecyclage(@Valid @RequestBody RecyclageDto recyclageDto){
        return ResponseEntity.ok(recyclageService.addRecyclage(recyclageDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RecyclageDto> removeRecyclage(@PathVariable Long id){
        return ResponseEntity.ok(recyclageService.removeRecyclage(id));
    }

}
