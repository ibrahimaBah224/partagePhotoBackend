package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.PasswordDto;
import SPA.dev.Stock.dto.RegisterUserDto;
import SPA.dev.Stock.dto.UserDtoEnvoie;
import SPA.dev.Stock.enumeration.EnumTypeMagasin;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.mapper.UserMapper1;
import SPA.dev.Stock.modele.Fournisseur;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.FournisseurRepository;
import SPA.dev.Stock.repository.MagasinRepository;
import SPA.dev.Stock.repository.UserRepository;
import SPA.dev.Stock.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final UserMapper1 userMapper;
    private final MagasinRepository magasinRepository;
    private final FournisseurRepository fournisseurRepository;

    @GetMapping("/list")
    public List<UserDtoEnvoie> getAllUsers() {
        return  userService.allUsers();
    }

    @PostMapping("/add")
    public UserDtoEnvoie registerUser(@RequestBody RegisterUserDto registerUserDto) {
        return  userService.registerUser(registerUserDto);
    }

    @PutMapping("update/{id}")
    public UserDtoEnvoie updateUser(@PathVariable int id, @RequestBody RegisterUserDto registerUserDto) {
        return userService.updateUser(id, registerUserDto);
    }

    @PutMapping("updatePassword/{id}")
    public UserDtoEnvoie updateUserPassword(@PathVariable int id, @RequestBody PasswordDto passwordDto) {
        return  userService.updateUserPassword(id, passwordDto);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getOne/{id}")
    public ResponseEntity<UserDtoEnvoie> getOne(@PathVariable int id){
        return ResponseEntity.ok(userService.findById(id));
    }
/*
   @PostConstruct
    public void addAdmin(){
        Optional<User> user = userRepository.findByTelephone("624085523");
        if(user.isPresent()) {
            throw new RuntimeException("ce numero est déja utilisé");
        }
        Magasin magasin = new Magasin();
        magasin.setNom("Magasin1");
        magasin.setAdresse("12 rue de la république");
        magasin.setReference("Ref-1");
        magasin.setTypeMagasin(EnumTypeMagasin.MAGASIN);
        magasin = magasinRepository.save(magasin);
        User userDto = new User();
        userDto.setEmail("admin@gmail.com");
        userDto.setRole(RoleEnumeration.valueOf("SUPER_ADMIN"));
        userDto.setFullName("admin");
        userDto.setMagasin(magasin);
        userDto.setTelephone("624085523");
        userDto.setPassword(bCryptPasswordEncoder.encode("admin"));
        User savedUser = userRepository.save(userDto);
        if (savedUser == null) {
            throw new IllegalArgumentException("L'utilisateur n'a pas pu être enregistré.");
        }
        savedUser.setCreatedBy(savedUser.getId());
        userRepository.save(savedUser);
       Fournisseur fournisseur = new Fournisseur();
       fournisseur.setAdresse(userDto.getEmail());
       fournisseur.setNom(userDto.getFullName());
       fournisseur.setTel(userDto.getTelephone());
       fournisseur.setCreatedBy(savedUser.getId());
       fournisseur.setSociete("Magasin Principal");
       fournisseurRepository.save(fournisseur);
    }
*/
}
