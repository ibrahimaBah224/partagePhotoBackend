package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.VenteInitDto;
import SPA.dev.Stock.enumeration.EnumEtatCommande;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.Mapper;
import SPA.dev.Stock.modele.Client;
import SPA.dev.Stock.modele.VenteInit;
import SPA.dev.Stock.repository.ClientRepository;
import SPA.dev.Stock.repository.VenteInitRepository;
import SPA.dev.Stock.repository.VenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static SPA.dev.Stock.config.RandomStringGenerator.generateRandomString;

@Service
@RequiredArgsConstructor
public class VenteInitService {

    private final VenteInitRepository venteInitRepository;
    private final ClientRepository clientRepository; // Injection de ClientRepository

    private final Mapper venteInitMapper; // Injection de Mapper
    private final UserService userService;


    public List<VenteInitDto> getAll() {
        int currentUserId = userService.getCurrentUserId();
        List<VenteInit> ventes = venteInitRepository.findAllByCreatedBy(currentUserId);
        return ventes.stream()
                .map(venteInitMapper::toDto)
                .collect(Collectors.toList());
    }

    public VenteInitDto getLastInitVente() {
        int currentUserId = userService.getCurrentUserId();
        Optional<VenteInit> lastVente = venteInitRepository.findAllByCreatedBy(currentUserId)
                .stream()
                .sorted(Comparator.comparing(VenteInit::getCreatedAt).reversed())
                .findFirst();

        if (lastVente.isPresent()) {
            return venteInitMapper.toDto(lastVente.get());
        } else {
            throw new AppException("Pas de dernière vente initialisée par cet utilisateur", HttpStatus.NOT_FOUND);
        }
    }


    public VenteInitDto getVenteInit(Long id) {
        int currentUserId = userService.getCurrentUserId();
        VenteInit vente = venteInitRepository.findByIdAndCreatedBy(id, currentUserId);
        return venteInitMapper.toDto(vente);
    }


    public VenteInitDto addVenteInit(VenteInitDto venteInitDto) {
        List<VenteInit> venteInitList = venteInitRepository.findAll();
        final String[] reference = new String[1];
        boolean exists;

        // Boucle pour générer une référence unique
        do {
            reference[0] = "VTE-" + generateRandomString(5);
            exists = venteInitList.stream()
                    .anyMatch(compte -> compte.getReference().equals(reference[0]));
        } while (exists);
       // Recuperation de l'utilisateur par défaut
        Optional<Client> existingClient= clientRepository.findById(1L);

        // voir si l'utilisateur par défaut existe ?
        if (!existingClient.isPresent()){

          Client newClient = new Client();
          newClient.setNom("comptoir");
          newClient.setPrenom("default");
          newClient.setAdresse("default");

          newClient.setTelephone("default");
          newClient.setStatus(1);
          clientRepository.save(newClient);
            VenteInit venteInit = venteInitMapper.toEntity(venteInitDto, newClient);
            venteInit.setReference(reference[0]);
            venteInit.setStatus(1);
            VenteInit newVenteInit = venteInitRepository.save(venteInit);
            return venteInitMapper.toDto(newVenteInit);

        }
        else {
            Client client = clientRepository.findById(venteInitDto.getIdClient())
                    .orElseThrow(() -> new AppException("Le client n'existe pas", HttpStatus.NOT_FOUND));


            int currentUserId = userService.getCurrentUserId();

            VenteInit venteInit = venteInitMapper.toEntity(venteInitDto, client);
            venteInit.setReference("VTE-" + generateRandomString(5));
            venteInit.setCreatedBy(currentUserId); // Associe l'utilisateur connecté à la création
            venteInit.setStatus(1);
            VenteInit newVenteInit = venteInitRepository.save(venteInit);
            return venteInitMapper.toDto(newVenteInit);
        }
    }


    public VenteInitDto removeVenteInit(Long id) {
        int currentUserId = userService.getCurrentUserId();
        VenteInit venteInit = venteInitRepository.findByIdAndCreatedBy(id, currentUserId);

        venteInitRepository.deleteById(id);
        return venteInitMapper.toDto(venteInit);
    }


    public VenteInitDto updateVenteInit(Long id, VenteInitDto venteInitDto) {

        int currentUserId = userService.getCurrentUserId();
        VenteInit existingVente =  venteInitRepository.findByIdAndCreatedBy(id, currentUserId);

        Client client = clientRepository.findById(venteInitDto.getIdClient())
                .orElseThrow(() -> new AppException("Client not found", HttpStatus.NOT_FOUND));

        VenteInit venteInit = venteInitMapper.toEntity(venteInitDto, client);
        venteInit.setId(id);
        VenteInit updatedVenteInit = venteInitRepository.save(venteInit);
        return venteInitMapper.toDto(updatedVenteInit);
    }
    public  VenteInitDto updateStatutVenteInit(long id, int status){
        int currentUserId = userService.getCurrentUserId();
        VenteInit existingVente =  venteInitRepository.findByIdAndCreatedBy(id, currentUserId);
        existingVente.setStatus(status);
        return  venteInitMapper.toDto(venteInitRepository.save(existingVente));
    }
}
