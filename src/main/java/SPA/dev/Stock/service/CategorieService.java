package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.CategorieDto;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.mapper.CategorieMapper;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Categorie;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.CategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategorieService {
    private final CategorieRepository categorieRepository;
    private  final CategorieMapper categorieMapper;
    private final UserService userService;
    public CategorieDto ajouter(CategorieDto categorieDto) {
        categorieDto.setCreatedBy(userService.getCurrentUserId());
        Categorie categorie = categorieMapper.toEntity(categorieDto);
        return categorieMapper.toDto(categorieRepository.save(categorie));
    }

    public List<CategorieDto> liste() {
        List<Categorie> list = categorieRepository.findAll();
        return categorieMapper.toDtoList(list);
    }

    public Optional<CategorieDto> getCategorie(int id) {
        Categorie categorie = categorieRepository.findById(id).orElseThrow(()-> new RuntimeException("categorie introuvable") );
        return Optional.of(categorieMapper.toDto(categorie));
    }

    public String delete(int id) {
         categorieRepository.deleteById(id);
        return "categorie";
    }

    public CategorieDto modifier(int id, CategorieDto categorieDto) {
        Categorie categorie = categorieMapper.toEntity(categorieDto);
        categorie.setIdCategorie(id);
        return categorieMapper.toDto(categorieRepository.save(categorie));
    }
}
