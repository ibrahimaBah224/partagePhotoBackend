package SPA.dev.Stock.controller;
import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.enumeration.EnumTypeMagasin;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.UserRepository;
import SPA.dev.Stock.service.MagasinService;
import SPA.dev.Stock.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/entrepot")
@RequiredArgsConstructor
public class EntrepotController {
    private final MagasinService magasinService;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/add")
    public MagasinDto ajouter(@RequestBody MagasinDto magasinDto) {
        int currentId = userService.getCurrentUserId();
        User currentUser = userRepository.findById(currentId)
                .orElseThrow(()->new RuntimeException("user not found"));
        if (!RoleEnumeration.SUPER_ADMIN.equals(currentUser.getRole()) ){
            if(!currentUser.getRole().equals(RoleEnumeration.ADMIN)) {
                throw new RuntimeException("Vous n'êtes pas autorisé");
            }
        }
        magasinDto.setTypeMagasin(String.valueOf(EnumTypeMagasin.ENTREPOT));
        return magasinService.createMagasin(magasinDto);
    }

    @GetMapping("/list")
    public List<MagasinDto> getEntrepots(){
        return magasinService.list(EnumTypeMagasin.ENTREPOT);
    }

    @GetMapping("/getOne/{id}")
    public MagasinDto getEntrepotById(@PathVariable int id){
        return magasinService.getMagasinById(id,EnumTypeMagasin.ENTREPOT);
    }

    @PutMapping("/update/{id}")
    public MagasinDto updateEntrepot(@PathVariable int id,@RequestBody MagasinDto magasinDto){
        return magasinService.updateMagasin(id,magasinDto,EnumTypeMagasin.ENTREPOT);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEntrepot(@PathVariable int id){
        magasinService.deleteMagasin(id,EnumTypeMagasin.ENTREPOT);
        return "deleted";
    }
}

