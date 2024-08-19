package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.ClientDto;
import SPA.dev.Stock.modele.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toClient(ClientDto clientDto);
    ClientDto toClientDto(Client client);
    List<ClientDto> toClientDtoList(List<Client> clients);
}