package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.RegisterUserDto;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.UserRepository;
import SPA.dev.Stock.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private  UserService userService;

    public UserController(UserService userService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @GetMapping("/listUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        User newUser = userService.registerUser(registerUserDto);
        newUser.setCreatedBy(userService.getCurrentUserId());
        userRepository.save(newUser);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody RegisterUserDto registerUserDto) {
        User updatedUser = userService.updateUser(id, registerUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("updatePassword/{id}/password")
    public ResponseEntity<User> updateUserPassword(@PathVariable int id, @RequestBody RegisterUserDto registerUserDto) {
        User updatedUser = userService.updateUserPassword(id, registerUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PostConstruct
    public void addAdmin(){
        if(!userRepository.findByTelephone("624085523").isPresent()) {
            User userDto = new User();
            userDto.setEmail("admin@gmail.com");
            userDto.setRole(RoleEnumeration.valueOf("SUPER_ADMIN"));
            userDto.setFullName("admin");
            userDto.setTelephone("624085523");
            userDto.setPassword(bCryptPasswordEncoder.encode("admin"));

            User savedUser = userRepository.save(userDto);
            if (savedUser == null) {
                throw new IllegalArgumentException("L'utilisateur n'a pas pu être enregistré.");
            }
            savedUser.setCreatedBy(savedUser.getId());
            userRepository.save(savedUser);
        }
    }

}
