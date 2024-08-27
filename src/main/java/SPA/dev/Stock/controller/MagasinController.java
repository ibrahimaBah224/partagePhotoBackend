package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.MagasinDto;

import SPA.dev.Stock.service.MagasinService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/magasin")
@AllArgsConstructor
public class MagasinController {
    private MagasinService magasinService;
    @PostMapping("/add")
    public MagasinDto addMagasin(@RequestBody MagasinDto magasinDto){
        return magasinService.createMagasin(magasinDto);
    }
     @GetMapping("/list")
     public List<MagasinDto> getMagasins(){
        return magasinService.list();
    }
    @GetMapping("/getOne/{id}")
    public MagasinDto getMagasinById(@PathVariable int id){
        return magasinService.getMagasinById(id);
    }
    @PutMapping("/update/{id}")
    public MagasinDto updateMagasin(@PathVariable int id,@RequestBody MagasinDto magasinDto){
        return magasinService.updateMagasin(id,magasinDto);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteMagasin(@PathVariable int id){
        magasinService.deleteMagasin(id);
        return "deleted";

    }
}
