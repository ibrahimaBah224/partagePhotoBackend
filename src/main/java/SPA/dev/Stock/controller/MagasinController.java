package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.MagasinDto;

import SPA.dev.Stock.enumeration.EnumTypeMagasin;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.UserRepository;
import SPA.dev.Stock.service.MagasinService;
import SPA.dev.Stock.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/magasin")
@AllArgsConstructor
public class MagasinController {
    private final UserService userService;
    private final UserRepository userRepository;
    private MagasinService magasinService;
    @PostMapping("/add")
    public MagasinDto addMagasin(@RequestBody MagasinDto magasinDto){
        int currentId = userService.getCurrentUserId();
        User currentUser = userRepository.findById(currentId)
                .orElseThrow(()->new RuntimeException("user not found"));
        if (!RoleEnumeration.SUPER_ADMIN.equals(currentUser.getRole())){
            throw new RuntimeException("Vous n'êtes pas autorisé");
        }
        magasinDto.setTypeMagasin(String.valueOf(EnumTypeMagasin.MAGASIN));
        return magasinService.createMagasin(magasinDto);
    }
     @GetMapping("/list")
     public List<MagasinDto> getMagasins(){
        return magasinService.list(EnumTypeMagasin.MAGASIN);
    }
    @GetMapping("/getOne/{id}")
    public MagasinDto getMagasinById(@PathVariable int id){
        return magasinService.getMagasinById(id,EnumTypeMagasin.MAGASIN);
    }
    @PutMapping("/update/{id}")
    public MagasinDto updateMagasin(@PathVariable int id,@RequestBody MagasinDto magasinDto){
        return magasinService.updateMagasin(id,magasinDto,EnumTypeMagasin.MAGASIN);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteMagasin(@PathVariable int id){
        magasinService.deleteMagasin(id,EnumTypeMagasin.MAGASIN);
        return "deleted";
    }
}
