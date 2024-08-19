package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.dto.RegisterUserDto;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.modele.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    MagasinDto userToUserDTO(RegisterUserDto userDto);

    Magasin userDTOToUser(RegisterUserDto userDto);

    List<RegisterUserDto> usersToUserDTOs(List<User> users);

    User toEntity(RegisterUserDto registerUserDto);
}
