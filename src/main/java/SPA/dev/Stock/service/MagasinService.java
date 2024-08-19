package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.mapper.MagasinMapper;
import SPA.dev.Stock.modele.Fournisseur;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.repository.MagasinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MagasinService {
    private final MagasinMapper magasinMapper;
    private final MagasinRepository magasinRepository;
    public MagasinDto ajouter(MagasinDto magasinDto) {
        Magasin magasin = magasinMapper.toEntity(magasinDto);
        return magasinMapper.toDto(magasinRepository.save(magasin));
    }

    public List<MagasinDto> liste() {
        return magasinMapper.toDtoList(magasinRepository.findAll());
    }

    public Optional<MagasinDto> getMagasin(int id) {
        Magasin magasin = magasinRepository.findById(id).orElseThrow(()-> new RuntimeException("magasin introuvable"));
        return Optional.of(magasinMapper.toDto(magasin));
    }

    public String delete(int id) {
        magasinRepository.deleteById(id);
        return "suppression effectuer avec success";
    }

    public MagasinDto modifier(int id, MagasinDto magasinDto) {
        Magasin magasin = magasinMapper.toEntity(magasinDto);
        magasin.setIdMagasin(id);
        return magasinMapper.toDto(magasinRepository.save(magasin));
    }
}
