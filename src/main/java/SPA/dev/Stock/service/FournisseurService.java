package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.FournisseurDto;
import SPA.dev.Stock.mapper.FournisseurMapper;
import SPA.dev.Stock.modele.Fournisseur;
import SPA.dev.Stock.repository.FournisseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FournisseurService {
    private final FournisseurMapper fournisseurMapper;
    private final FournisseurRepository fournisseurRepository;
    private final UserService userService;
    public FournisseurDto ajouter(FournisseurDto fournisseurDto) {
        Fournisseur fournisseur = fournisseurMapper.toEntity(fournisseurDto);

        return fournisseurMapper.toDto(fournisseurRepository.save(fournisseur));
    }

    public List<FournisseurDto> liste() {
        int userId=userService.getCurrentUserId();
        return fournisseurMapper.toDtoList(fournisseurRepository.findFournisseursByCreatedBy(userId));
    }

    public Optional<FournisseurDto> getFournisseur(int id) {
        int userId = userService.getCurrentUserId();
        Fournisseur fournisseur = fournisseurRepository.findFournisseurByIdFournissseurAndCreatedBy(id,userId).orElseThrow(()->new RuntimeException("fournisseur introuvable"));
        return Optional.of(fournisseurMapper.toDto(fournisseur));
    }

    public String delete(int id) {
        getFournisseur(id);
        fournisseurRepository.deleteById(id);
        return "suppression effectuer avec success";
    }

    public FournisseurDto modifier(int id, FournisseurDto fournisseurDto) {
        getFournisseur(id);
        Fournisseur fournisseur = fournisseurMapper.toEntity(fournisseurDto);
        fournisseur.setIdFournissseur(id);
        return fournisseurMapper.toDto(fournisseurRepository.save(fournisseur));
    }
}
