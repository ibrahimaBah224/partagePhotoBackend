package SPA.dev.Stock.mapper;


import SPA.dev.Stock.dto.ClientDto;
import SPA.dev.Stock.modele.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto toDto(Client client);
    Client toEntity(ClientDto clientDto);
    List<ClientDto> toDtoList(List<Client> client);
}
