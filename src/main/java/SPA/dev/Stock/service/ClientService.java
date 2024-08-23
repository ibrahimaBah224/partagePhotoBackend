package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.ClientDto;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.ClientMapper;
import SPA.dev.Stock.modele.Client;
import SPA.dev.Stock.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public List<ClientDto> getAll(){
        List<Client> clients = clientRepository.findAll();
        return clientMapper.toClientDtoList(clients);
    }

    public ClientDto getClient(Long id){
        Client client = clientRepository.findById(id)
                .orElseThrow(()-> new AppException("Client not found", HttpStatus.NOT_FOUND));
        return clientMapper.toClientDto(client);
    }

    public ClientDto addClient(ClientDto clientDto){
        Client client = clientMapper.toClient(clientDto);
        Client newWlient= clientRepository.save(client);
        return clientMapper.toClientDto(newWlient);
    }

    public ClientDto removeClient(Long id){

        Client client = clientRepository.findById(id)
                .orElseThrow(()-> new AppException("Client not found", HttpStatus.NOT_FOUND));
        clientRepository.deleteById(id);
        return clientMapper.toClientDto(client);
    }

    public ClientDto updateClient(Long id, ClientDto clientDto){
        Client client = clientRepository.findById(id)
                .orElseThrow(()-> new AppException("Client not found", HttpStatus.NOT_FOUND));
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setAdresse(clientDto.getAdresse());
        client.setTelephone(clientDto.getTelephone());
        return clientMapper.toClientDto(clientRepository.save(client));
    }
}
