package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.dto.PasswordDto;
import SPA.dev.Stock.dto.RegisterUserDto;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.exception.UserNotFoundException;
import SPA.dev.Stock.mapper.UserMapper;
import SPA.dev.Stock.mapper.UserMapper1;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.MagasinRepository;
import SPA.dev.Stock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper1 userMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MagasinRepository magasinRepository;

    public int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the authentication object is present and valid
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new AccessDeniedException("User is not authenticated.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByTelephone(userDetails.getUsername())
                .orElseThrow(() -> new AccessDeniedException("User not found."))
                .getId();
    }

    public RegisterUserDto findById(int currentUserId) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(()->new RuntimeException("user not found"));
        return userMapper.toDto(user);
    }
    public List<RegisterUserDto> allUsers() {
        int currentUserId = getCurrentUserId();
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            if (user.getCreatedBy() == currentUserId) {
                users.add(user);
            }
        });
        return userMapper.toUserDtoList(users);
    }

    public RegisterUserDto updateUser(int id, RegisterUserDto registerUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("user not found"));
        int currentUserId = getCurrentUserId();
        if (user.getCreatedBy() != currentUserId && user.getId()!= currentUserId ) {
            throw new AccessDeniedException("You do not have permission to update this user.");
        }
        user.setFullName(registerUserDto.getFullName());
        user.setEmail(registerUserDto.getEmail());
        user.setTelephone(registerUserDto.getTelephone());
        return userMapper.toDto(userRepository.save(user));
    }

    public RegisterUserDto updateUserPassword(int id, PasswordDto passwordDto) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("user not found"));
        int currentUserId = getCurrentUserId();
        if (user.getCreatedBy() != currentUserId && user.getId()!= currentUserId ) {
            throw new AccessDeniedException("You do not have permission to update this user's password.");
        }
        user.setPassword(passwordDto.getPassword());
        return userMapper.toDto(userRepository.save(user));
    }

    public RegisterUserDto registerUser(RegisterUserDto registerUserDto) {
        int currentUserId = getCurrentUserId(); // Fetch the ID of the currently authenticated user
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new AccessDeniedException("Current user not found."));
        if (!currentUser.getRole().equals(RoleEnumeration.SUPER_ADMIN)) {
            throw new AccessDeniedException("You do not have permission to perform this action.");
        }
        if (!userRepository.findByTelephone(registerUserDto.getTelephone()).isPresent()) {
            Magasin magasin = magasinRepository.findById(registerUserDto.getIdMagasin())
                    .orElseThrow(()->new RuntimeException("magasin not found"));
            User user = userMapper.toEntity(registerUserDto,magasin);
            user.setRole(RoleEnumeration.ADMIN); // Assign the ADMIN role to the new user
            user.setPassword(bCryptPasswordEncoder.encode(registerUserDto.getPassword()));
            user.setCreatedBy(getCurrentUserId());
            if (magasin.getUser()!=null) {
                throw new RuntimeException("ce magasin est déjà affecté a quelqu'un");
            }
            user = userRepository.save(user);
            magasin.setUser(user);
            if(magasinRepository.save(magasin)==null){
                userRepository.delete(user);
            }
            magasinRepository.save(magasin);
            return userMapper.toDto(user);
        }
        throw new RuntimeException("User already exists with this telephone number.");
    }

    public void deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
        int currentUserId = getCurrentUserId();
        if (user.getCreatedBy() != currentUserId) {
            throw new AccessDeniedException("You do not have permission to delete this user.");
        }
        userRepository.delete(user);
    }
}
