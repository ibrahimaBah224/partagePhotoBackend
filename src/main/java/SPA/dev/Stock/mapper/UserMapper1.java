package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.*;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.modele.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class UserMapper1 {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public RegisterUserDto toDto(User user) {
        return RegisterUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .idMagasin(user.getMagasin().getId())
                .telephone(user.getTelephone())
                .Role(String.valueOf(user.getRole()))
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .build();
    }

    public User toEntity(RegisterUserDto registerUserDto,Magasin magasin) {
        return User.builder()
                .email(registerUserDto.getEmail())
                .magasin(magasin)
                .fullName(registerUserDto.getFullName())
                .telephone(registerUserDto.getTelephone())
                .role(RoleEnumeration.valueOf(registerUserDto.getRole()))
                .build();
    }

    public List<RegisterUserDto> toUserDtoList(List<User> users) {
        return users.stream().map(this::toDto).collect(toList());
    }

}