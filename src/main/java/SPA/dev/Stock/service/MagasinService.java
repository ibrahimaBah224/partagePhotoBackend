package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.exception.MagasinNotFoundException;
import SPA.dev.Stock.exception.UserNotFoundException;
import SPA.dev.Stock.mapper.MagasinMapper;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.MagasinRepository;


import SPA.dev.Stock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagasinService {
    private final MagasinRepository magasinRepository;
    private final UserService userService;
    private final MagasinMapper magasinMapper;
    private final UserRepository userRepository;

    public MagasinService(MagasinRepository magasinRepository, UserService userService, MagasinMapper magasinMapper, UserRepository userRepository) {
        this.magasinRepository = magasinRepository;
        this.userService = userService;
        this.magasinMapper = magasinMapper;
        this.userRepository = userRepository;
    }


    public MagasinDto getMagasinsForCurrentUser() {
        int currentUserId = userService.getCurrentUserId();
        Magasin magasins = magasinRepository.findByUserId(currentUserId);
        return magasinMapper.magasinToMagasinDTO(magasins);
    }
    public List<MagasinDto> list(){
        List<Magasin> magasins = magasinRepository.findAllByIdOrCreatedBy(userService.getCurrentUserId(),userService.getCurrentUserId());
        return magasinMapper.magasinsToMagasinDTOs(magasins);
    }
    public MagasinDto createMagasin(MagasinDto magasinDTO) {
        User user = userRepository.findById(userService.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!RoleEnumeration.SUPER_ADMIN.name().equalsIgnoreCase(String.valueOf(user.getRole()))) {
            throw new RuntimeException("Vous n'êtes pas autorisé");
        }
        Magasin magasin = magasinMapper.magasinDTOToMagasin(magasinDTO);
        Magasin savedMagasin = magasinRepository.save(magasin);
        return magasinMapper.magasinToMagasinDTO(savedMagasin);
    }


    public MagasinDto getMagasinById(int id) {
        int currentUserId = userService.getCurrentUserId();
        Magasin magasin = magasinRepository.findByIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new MagasinNotFoundException("Magasin not found or access denied"));
        return magasinMapper.magasinToMagasinDTO(magasin);
    }
    public MagasinDto getMagasin(int id){
        Magasin magasin = magasinRepository.findById(id)
                .orElseThrow(()->new RuntimeException("magasin not found"));
        return magasinMapper.magasinToMagasinDTO(magasin);
    }

    public MagasinDto updateMagasin(int id, MagasinDto magasinDTO) {
        int currentUserId = userService.getCurrentUserId();
        Magasin magasin = magasinRepository.findByIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new MagasinNotFoundException("Magasin not found or access denied"));
        magasin.setNom(magasinDTO.getNom());
        magasin.setReference(magasinDTO.getReference());
        magasin.setAdresse(magasinDTO.getAdresse());
        Magasin updatedMagasin = magasinRepository.save(magasin);
        return magasinMapper.magasinToMagasinDTO(updatedMagasin);
    }

    public void deleteMagasin(int id) {
       /* int currentUserId = userService.getCurrentUserId();
        Magasin magasin = magasinRepository.findByIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new MagasinNotFoundException("Magasin not found or access denied"));
        magasinRepository.delete(magasin);*/

        Magasin magasin = magasinRepository.findById(id)
                .orElseThrow(()->new RuntimeException("magasin not found"));
        if(magasin.getCreatedBy() != userService.getCurrentUserId()){
            throw new RuntimeException("access denied");
        }
        if (magasin.getUser()!= null){
            throw new RuntimeException("ce magasin est déjà lié à un Gestionnaire");
        }
        magasinRepository.deleteById(id);
    }
}
