package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.ProduitDto;
import SPA.dev.Stock.mapper.ProduitMapper;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitMapper produitMapper;
    private final SousCategorieService sousCategorieService;
    private final ProduitRepository produitRepository;
    public ProduitDto ajouter(ProduitDto produitDto) {
        Produit produit = produitMapper.toEntity(produitDto);
        sousCategorieService.getSousCategorie(produitDto.getId_sousCategorie());
        return produitMapper.toDto(produitRepository.save(produit));
    }

    public List<ProduitDto> liste() {
        return produitMapper.toDtoList(produitRepository.findAll());
    }

    public Optional<ProduitDto> getProduit(int id) {
        Produit produit = produitRepository.findById(id).orElseThrow(()->new RuntimeException("produit non trouver"));
        return Optional.of(produitMapper.toDto(produit));
    }

    public String delete(int id) {
        getProduit(id);
        produitRepository.deleteById(id);
        return "produit supprimer avec success";
    }

    public ProduitDto modifier(int id, ProduitDto produitDto) {
        Produit produit = produitMapper.toEntity(produitDto);
        sousCategorieService.getSousCategorie(produitDto.getId_sousCategorie());
        produit.setIdProduit(id);
        return produitMapper.toDto(produitRepository.save(produit));
    }
}
