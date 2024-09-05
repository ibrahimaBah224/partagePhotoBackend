package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.AchatInitDto;
import SPA.dev.Stock.dto.VenteInitDto;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.Mapper;
import SPA.dev.Stock.modele.AchatInit;
import SPA.dev.Stock.modele.Client;
import SPA.dev.Stock.modele.VenteInit;
import SPA.dev.Stock.repository.AchatInitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static SPA.dev.Stock.config.RandomStringGenerator.generateRandomString;

@Service
@RequiredArgsConstructor
public class AchatInitService {
    private final AchatInitRepository achatInitRepository;
    private final Mapper achatInitMapper;
    private final UserService userService;

        public List<AchatInitDto> getAll() {
        int currentUserId = userService.getCurrentUserId();
        List<AchatInit> achats = achatInitRepository.findAllByCreatedBy(currentUserId);
        return achats.stream()
                .map(achatInitMapper::toAchatInitDto)
                .collect(Collectors.toList());
    }

    public AchatInitDto getLastInitAchat() {
        int currentUserId = userService.getCurrentUserId();
        Optional<AchatInit> lastAchat = achatInitRepository.findAllByCreatedBy(currentUserId)
                .stream()
                .sorted(Comparator.comparing(AchatInit::getCreatedAt).reversed())
                .findFirst();

        if (lastAchat.isPresent()) {
            return achatInitMapper.toAchatInitDto(lastAchat.get());
        } else {
            throw new AppException("Pas de dernier achat initialisée par cet utilisateur", HttpStatus.NOT_FOUND);
        }
    }


    public AchatInitDto getAchatInit(Long id) {
        int currentUserId = userService.getCurrentUserId();
        AchatInit achatInit = achatInitRepository.findByIdAndCreatedBy(id, currentUserId);
        return achatInitMapper.toAchatInitDto(achatInit);
    }


    public AchatInitDto addAchatInit(AchatInitDto venteInitDto) {
        List<AchatInit> achatInitList = achatInitRepository.findAll();
        final String[] reference = new String[1];
        boolean exists;

        // Boucle pour générer une référence unique
        do {
            reference[0] = "ACH-" + generateRandomString(5);
            exists = achatInitList.stream()
                    .anyMatch(achatInit -> achatInit.getReference().equals(reference[0]));
        } while (exists);

            int currentUserId = userService.getCurrentUserId();

            AchatInit achatInit = achatInitMapper.toAchatInitEntity(venteInitDto);
            achatInit.setReference("VTE-" + generateRandomString(5));
            achatInit.setCreatedBy(currentUserId);
            achatInit.setStatus(1);
            AchatInit newAchat = achatInitRepository.save(achatInit);
            return achatInitMapper.toAchatInitDto(newAchat);
    }
    public AchatInitDto removeAchatInit(Long id) {
        int currentUserId = userService.getCurrentUserId();
        AchatInit achatInit = achatInitRepository.findByIdAndCreatedBy(id, currentUserId);

        achatInitRepository.deleteById(id);
        return achatInitMapper.toAchatInitDto(achatInit);
    }



    public  AchatInitDto updateStatutAchatInit(long id, int status){
        int currentUserId = userService.getCurrentUserId();
        AchatInit existingAchat =  achatInitRepository.findByIdAndCreatedBy(id, currentUserId);
        existingAchat.setStatus(status);
        return  achatInitMapper.toAchatInitDto(achatInitRepository.save(existingAchat));
    }

}






