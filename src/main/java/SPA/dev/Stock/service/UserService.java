package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.dto.RegisterUserDto;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.exception.UserNotFoundException;
import SPA.dev.Stock.mapper.UserMapper;
import SPA.dev.Stock.modele.User;
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
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println(STR."=======================================\n========================\n \{userRepository.findByTelephone(userDetails.getUsername()).get().getId()}");
            return userRepository.findByTelephone(userDetails.getUsername()).get().getId();
        }
        return -1;
    }

    public Optional<User> findById(int currentUserId) {
        return userRepository.findById(currentUserId);
    }
    public List<User> allUsers() {
        int currentUserId = getCurrentUserId();
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            if (user.getCreatedBy() == currentUserId) {
                users.add(user);
            }
        });
        return users;
    }

    public User updateUser(int id, RegisterUserDto registerUserDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            int currentUserId = getCurrentUserId();
            if (user.getCreatedBy() != currentUserId && user.getId()!= currentUserId ) {
                throw new AccessDeniedException("You do not have permission to update this user.");
            }
            user.setFullName(registerUserDto.getFullName());
            user.setEmail(registerUserDto.getEmail());
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found with id " + id);
        }
    }

    public User updateUserPassword(int id, RegisterUserDto registerUserDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            int currentUserId = getCurrentUserId();
            if (user.getCreatedBy() != currentUserId && user.getId()!= currentUserId ) {
                throw new AccessDeniedException("You do not have permission to update this user's password.");
            }
            user.setPassword(registerUserDto.getPassword());
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found with id " + id);
        }
    }

    public User registerUser(RegisterUserDto registerUserDto) {
        if(!userRepository.findByTelephone(registerUserDto.getTelephone()).isPresent()) {
            User user = userMapper.toEntity(registerUserDto);
            user.setRole(RoleEnumeration.valueOf("ADMIN"));
            user.setPassword(bCryptPasswordEncoder.encode(registerUserDto.getPassword()));
            return userRepository.save(user);
        }
        return null;
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
