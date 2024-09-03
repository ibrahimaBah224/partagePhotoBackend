package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.dto.PasswordDto;
import SPA.dev.Stock.dto.RegisterUserDto;
import SPA.dev.Stock.dto.UserDtoEnvoie;
import SPA.dev.Stock.enumeration.EnumTypeMagasin;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.exception.UserNotFoundException;
import SPA.dev.Stock.fonction.Fonction;
import SPA.dev.Stock.mapper.UserMapper;
import SPA.dev.Stock.mapper.UserMapper1;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.MagasinRepository;
import SPA.dev.Stock.repository.UserRepository;
import jakarta.transaction.Transactional;
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


    public boolean isValidRoleEnumeration(String value) {
        try {
            RoleEnumeration.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

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

    public UserDtoEnvoie findById(int currentUserId) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(()->new RuntimeException("user not found"));
        return userMapper.toDto(user);
    }
    public List<UserDtoEnvoie> allUsers() {
        int currentUserId = getCurrentUserId();
        List<User> users = userRepository.findAllByCreatedBy(currentUserId);
        return userMapper.toUserDtoList(users);
    }

    public UserDtoEnvoie updateUser(int id, RegisterUserDto registerUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("user not found"));
        Magasin magasin = magasinRepository.findById(registerUserDto.getIdMagasin())
                .orElseThrow(()->new RuntimeException("Magasin not found"));
        int currentUserId = getCurrentUserId();
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new AccessDeniedException("Current user not found."));

        if (user.getCreatedBy() != currentUserId && user.getId()!= currentUserId ) {
            throw new AccessDeniedException("You do not have permission to update this user.");
        }
        if(registerUserDto.getRole().equals(RoleEnumeration.SUPER_ADMIN)){
            throw  new RuntimeException("You do not have permission");
        }
        if(currentUser.getRole().equals(RoleEnumeration.ADMIN) && magasin.getTypeMagasin().equals(EnumTypeMagasin.MAGASIN)){
            throw  new RuntimeException("You do not have permission");
        }
        if(!magasin.getTypeMagasin().equals(EnumTypeMagasin.MAGASIN)){
            user.setRole(RoleEnumeration.valueOf(String.valueOf(user.getMagasin().getTypeMagasin())));
        }else{
            user.setRole(RoleEnumeration.ADMIN);
        }
        user = Fonction.updateEntityWithNonNullFields(user, registerUserDto,"id");
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDtoEnvoie updateUserPassword(int id, PasswordDto passwordDto) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("user not found"));
        int currentUserId = getCurrentUserId();
        if (user.getCreatedBy() != currentUserId && user.getId()!= currentUserId ) {
            throw new AccessDeniedException("You do not have permission to update this user's password.");
        }
        user.setPassword(passwordDto.getPassword());
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDtoEnvoie registerUser(RegisterUserDto registerUserDto) {
        int currentUserId = getCurrentUserId(); // Fetch the ID of the currently authenticated user
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new AccessDeniedException("Current user not found."));

        RoleEnumeration currentUserRole = currentUser.getRole();

        if (!(RoleEnumeration.SUPER_ADMIN.equals(currentUserRole) || RoleEnumeration.ADMIN.equals(currentUserRole))) {
            throw new AccessDeniedException("You do not have permission to perform this action.");
        }

        RoleEnumeration newUserRole = RoleEnumeration.valueOf(registerUserDto.getRole());

        if (!isValidRoleEnumeration(newUserRole.name())) {
            throw new RuntimeException("Invalid role.");
        }

        if (RoleEnumeration.ADMIN.equals(currentUserRole) &&
                (RoleEnumeration.ADMIN.equals(newUserRole) || RoleEnumeration.SUPER_ADMIN.equals(newUserRole))) {
            throw new RuntimeException("Admins cannot create another Admin or Super Admin.");
        }

        userRepository.findByTelephone(registerUserDto.getTelephone())
                .ifPresent(user -> {
                    throw new RuntimeException("User already exists with this telephone number.");
                });

        Magasin magasin = magasinRepository.findByIdAndCreatedBy(registerUserDto.getIdMagasin(), currentUserId)
                .orElseThrow(() -> new RuntimeException("Magasin not found."));

        if (magasin.getUser() != null) {
            throw new RuntimeException("This magasin is already assigned to another user.");
        }
        if(!currentUserRole.equals(RoleEnumeration.SUPER_ADMIN) || !currentUserRole.equals(RoleEnumeration.SUPER_ADMIN)) {
            if (registerUserDto.getRole().equals(magasin.getTypeMagasin())) {
                throw new RuntimeException("veuillez faire correspondre le user au role");
            }
        }
        User user = userMapper.toEntity(registerUserDto, magasin);
        user.setRole(newUserRole);
        user.setPassword(bCryptPasswordEncoder.encode(registerUserDto.getPassword()));
        user.setCreatedBy(currentUserId);

        User savedUser = userRepository.save(user);

        magasin.setUser(savedUser);

        try {
            magasinRepository.save(magasin);
        } catch (Exception e) {
            userRepository.delete(savedUser); // Clean up the user if the magasin update fails
            throw new RuntimeException("Failed to save magasin. User creation rolled back.");
        }

        return userMapper.toDto(savedUser);
    }

    @Transactional
    public void deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
        int currentUserId = getCurrentUserId();
        if (user.getCreatedBy() != currentUserId) {
            throw new AccessDeniedException("You do not have permission to delete this user.");
        }
        Magasin magasin = magasinRepository.findById(user.getMagasin().getId())
                .orElseThrow(()->new RuntimeException("magasin for user not found"));
        magasin.setUser(null);
        magasinRepository.save(magasin);
        userRepository.delete(user);
    }
}
