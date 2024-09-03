package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.enumeration.EnumTypeMagasin;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.exception.MagasinNotFoundException;
import SPA.dev.Stock.mapper.MagasinMapper;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.MagasinRepository;


import SPA.dev.Stock.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MagasinService {
    private final MagasinRepository magasinRepository;
    private final UserService userService;
    private final MagasinMapper magasinMapper;


    public boolean isValidEnumTypeMagasin(String value) {
        try {
            EnumTypeMagasin.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }



    public MagasinDto getMagasinsForCurrentUser() {
        int currentUserId = userService.getCurrentUserId();
        Magasin magasins = magasinRepository.findByUserId(currentUserId);
        return magasinMapper.magasinToMagasinDTO(magasins);
    }
    public List<MagasinDto> list(EnumTypeMagasin typeMagasin){
        List<Magasin> magasins = magasinRepository.findAllByCreatedByAndTypeMagasin(userService.getCurrentUserId(),typeMagasin);
        return magasinMapper.magasinsToMagasinDTOs(magasins);
    }
    public MagasinDto createMagasin(MagasinDto magasinDTO) {
        Magasin magasin = magasinMapper.magasinDTOToMagasin(magasinDTO);
        Magasin savedMagasin = magasinRepository.save(magasin);
        return magasinMapper.magasinToMagasinDTO(savedMagasin);
    }


    public MagasinDto getMagasinById(int id,EnumTypeMagasin typeMagasin) {
        int currentUserId = userService.getCurrentUserId();
        Magasin magasin = magasinRepository.findByIdAndCreatedByAndTypeMagasin(id, currentUserId,typeMagasin)
                .orElseThrow(() -> new MagasinNotFoundException(STR."\{typeMagasin} not found or access denied"));
        return magasinMapper.magasinToMagasinDTO(magasin);
    }
    public MagasinDto updateMagasin(int id, MagasinDto magasinDTO,EnumTypeMagasin enumTypeMagasin) {
        int currentUserId = userService.getCurrentUserId();
        Magasin magasin = magasinRepository.findByIdAndCreatedByAndTypeMagasin(id, currentUserId,enumTypeMagasin)
                .orElseThrow(() -> new MagasinNotFoundException(STR."\{enumTypeMagasin} not found or access denied"));
        if(magasinDTO.getNom()!=null)
            magasin.setNom(magasinDTO.getNom());
        if (magasinDTO.getReference()!=null)
            magasin.setReference(magasinDTO.getReference());
        if(magasinDTO.getAdresse()!=null)
            magasin.setAdresse(magasinDTO.getAdresse());
        Magasin updatedMagasin = magasinRepository.save(magasin);
        return magasinMapper.magasinToMagasinDTO(updatedMagasin);
    }

    public void deleteMagasin(int id,EnumTypeMagasin typeMagasin) {
        Magasin magasin = magasinRepository.findByIdAndTypeMagasin(id,typeMagasin)
                .orElseThrow(()->new RuntimeException(STR."\{typeMagasin} not found"));
        if(magasin.getCreatedBy() != userService.getCurrentUserId()){
            throw new RuntimeException("access denied");
        }
        if (magasin.getUser()!= null){
            throw new RuntimeException(STR."\{typeMagasin} est déjà lié à un Gestionnaire");
        }
        magasinRepository.deleteById(id);
    }
}
