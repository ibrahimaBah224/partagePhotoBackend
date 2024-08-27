package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.SousCategorieDto;
import SPA.dev.Stock.mapper.CategorieMapper;
import SPA.dev.Stock.mapper.SousCategorieMapper;
import SPA.dev.Stock.modele.Categorie;
import SPA.dev.Stock.modele.SousCategorie;
import SPA.dev.Stock.repository.CategorieRepository;
import SPA.dev.Stock.repository.SousCategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SousCategorieService {
    private final SousCategorieRepository sousCategorieRepository;
    private final CategorieService categorieService;
    private final SousCategorieMapper sousCategorieMapper;
    private final UserService userService;
    private final CategorieMapper categorieMapper;
    public SousCategorieDto ajouter(SousCategorieDto sousCategorieDto) {
        SousCategorie sousCategorie = sousCategorieMapper.toEntity(sousCategorieDto);
        return sousCategorieMapper.toDto(sousCategorieRepository.save(sousCategorie));
    }

    public List<SousCategorieDto> liste() {
        List<SousCategorieDto> list = sousCategorieMapper.toDtoList(sousCategorieRepository.findAll());
        return list;
    }

    public Optional<SousCategorieDto> getSousCategorie(int id) {
        SousCategorie sousCategorie = sousCategorieRepository.findById(id).orElseThrow(()->new RuntimeException("sous categorie introuvable"));
        return Optional.of(sousCategorieMapper.toDto(sousCategorie));
    }

    public String delete(int id) {
        getSousCategorie(id);
        sousCategorieRepository.deleteById(id);
        return "supprimer avec succes";
    }

    public SousCategorieDto modifier(int id, SousCategorieDto sousCategorieDto) {
        getSousCategorie(id);
        SousCategorie sousCategorie = sousCategorieMapper.toEntity(sousCategorieDto);
        sousCategorie.setIdSousCategorie(id);
        return sousCategorieMapper.toDto(sousCategorieRepository.save(sousCategorie));
    }
}
