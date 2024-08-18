package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.LoginUserDto;
import SPA.dev.Stock.dto.RegisterUserDto;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
                user.setFullName(input.getFullName());
                user.setTelephone(input.getTelephone());
                user.setEmail(input.getEmail());
                user.setRole(input.getRole() == null? RoleEnumeration.SUPER_ADMIN : input.getRole() );
                user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getTelephone(),
                        input.getPassword()
                )
        );

        return userRepository.findByTelephone(input.getTelephone())
                .orElseThrow();
    }
}
