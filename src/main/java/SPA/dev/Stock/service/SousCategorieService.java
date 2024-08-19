package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.SousCategorieDto;
import SPA.dev.Stock.mapper.SousCategorieMapper;
import SPA.dev.Stock.modele.Produit;
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
    private final CategorieRepository categorieRepository;
    private final SousCategorieMapper sousCategorieMapper;
    public SousCategorieDto ajouter(SousCategorieDto sousCategorieDto) {
        SousCategorie sousCategorie = sousCategorieMapper.toEntity(sousCategorieDto);
        categorieRepository.findById(sousCategorieDto.getIdCategorie()).orElseThrow(()->new RuntimeException("cette categorie n'existe ppas"));
        return sousCategorieMapper.toDto(sousCategorieRepository.save(sousCategorie));
    }

    public List<SousCategorieDto> liste() {
        return sousCategorieMapper.toDtoList(sousCategorieRepository.findAll());
    }

    public Optional<SousCategorieDto> getSousCategorie(int id) {
        SousCategorie sousCategorie = sousCategorieRepository.findById(id).orElseThrow(()->new RuntimeException("sous categorie introuvable"));
        return Optional.of(sousCategorieMapper.toDto(sousCategorie));
    }

    public String delete(int id) {
        sousCategorieRepository.findById(id).orElseThrow(()->new RuntimeException("id introuvable"));
        sousCategorieRepository.deleteById(id);
        return "supprimer avec succes";
    }

    public SousCategorieDto modifier(int id, SousCategorieDto sousCategorieDto) {
        SousCategorie sousCategorie = sousCategorieRepository.findById(id).orElseThrow(()->new RuntimeException("sous categorie introuvble"));
        categorieRepository.findById(sousCategorieDto.getIdCategorie()).orElseThrow(()->new RuntimeException("cette categorie n'existe ppas"));
        sousCategorie.setIdSousCategorie(id);
        return sousCategorieMapper.toDto(sousCategorieRepository.save(sousCategorie));
    }
}
