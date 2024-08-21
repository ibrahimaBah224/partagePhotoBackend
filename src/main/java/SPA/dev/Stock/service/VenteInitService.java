package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.VenteInitDto;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.Mapper;
import SPA.dev.Stock.modele.Client;
import SPA.dev.Stock.modele.VenteInit;
import SPA.dev.Stock.repository.ClientRepository;
import SPA.dev.Stock.repository.VenteInitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VenteInitService {

    private final VenteInitRepository venteInitRepository;
    private final ClientRepository clientRepository; // Injection de ClientRepository

    private final Mapper venteInitMapper; // Injection de Mapper
    private final UserService userService;

    public List<VenteInitDto> getAll() {
        List<VenteInit> ventes = venteInitRepository.findAllByCreatedBy(userService.getCurrentUserId());
        return ventes.stream()
                .map(venteInitMapper::toDto)
                .collect(Collectors.toList());
    }

    public VenteInitDto getVenteInit(Long id) {
        VenteInit vente = (VenteInit) venteInitRepository.findByIdAndCreatedBy(id,userService.getCurrentUserId())
                .orElseThrow(() -> new AppException("Vente initial not found", HttpStatus.NOT_FOUND));
        return venteInitMapper.toDto(vente);
    }

    public VenteInitDto addVenteInit(VenteInitDto venteInitDto) {
        // Vous devrez obtenir l'entité Client ici pour l'utiliser dans le mapping
        Client client =  clientRepository.findById(venteInitDto.getIdClient())
                .orElseThrow(() -> new AppException("Client not found", HttpStatus.NOT_FOUND));// Code pour récupérer le client à partir de venteInitDto.getIdClient()

                VenteInit venteInit = venteInitMapper.toEntity(venteInitDto, client);
        venteInit.setReference(UUID.randomUUID().toString());
        VenteInit newVenteInit = venteInitRepository.save(venteInit);
        return venteInitMapper.toDto(newVenteInit);
    }

    public VenteInitDto removeVenteInit(Long id) {
        VenteInit venteInit = venteInitRepository.findById(id)
                .orElseThrow(() -> new AppException("Vente not found", HttpStatus.NOT_FOUND));
        venteInitRepository.deleteById(id);
        return venteInitMapper.toDto(venteInit);
    }

    public VenteInitDto updateVenteInit(Long id, VenteInitDto venteInitDto) {
        // Vous devrez obtenir l'entité Client ici pour l'utiliser dans le mapping
        Client client =clientRepository.findById(venteInitDto.getId())
                .orElseThrow(() -> new AppException("Client not found", HttpStatus.NOT_FOUND)); // Code pour récupérer le client à partir de venteInitDto.getIdClient()

                VenteInit venteInit = venteInitMapper.toEntity(venteInitDto, client);
        venteInit.setId(id); // Assurez-vous de définir l'ID pour la mise à jour
        VenteInit updatedVenteInit = venteInitRepository.save(venteInit);
        return venteInitMapper.toDto(updatedVenteInit);
    }
}
