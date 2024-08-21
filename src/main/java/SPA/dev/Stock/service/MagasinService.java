package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.exception.MagasinNotFoundException;
import SPA.dev.Stock.exception.UserNotFoundException;
import SPA.dev.Stock.mapper.MagasinMapper;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.MagasinRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MagasinService {
    private final MagasinRepository magasinRepository;
    private final UserService userService;
    private final MagasinMapper magasinMapper;


    public MagasinDto getMagasinsForCurrentUser() {
        int currentUserId = userService.getCurrentUserId();
        Magasin magasins = magasinRepository.findByUserId(currentUserId);
        return magasinMapper.magasinToMagasinDTO(magasins);
    }

    public MagasinDto createMagasin(MagasinDto magasinDTO) {
        int currentUserId = userService.getCurrentUserId();
        User user = userService.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));
        Magasin magasin = magasinMapper.magasinDTOToMagasin(magasinDTO);
        magasin.setUser(user);
        Magasin savedMagasin = magasinRepository.save(magasin);
        return magasinMapper.magasinToMagasinDTO(savedMagasin);
    }

    public MagasinDto getMagasinById(int id) {
        int currentUserId = userService.getCurrentUserId();
        Magasin magasin = magasinRepository.findByIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new MagasinNotFoundException("Magasin not found or access denied"));
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
        int currentUserId = userService.getCurrentUserId();
        Magasin magasin = magasinRepository.findByIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new MagasinNotFoundException("Magasin not found or access denied"));
        magasinRepository.delete(magasin);

    }
}
