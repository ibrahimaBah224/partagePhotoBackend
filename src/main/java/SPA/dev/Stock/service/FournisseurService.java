package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.FournisseurDto;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.fonction.Fonction;
import SPA.dev.Stock.mapper.FournisseurMapper;
import SPA.dev.Stock.modele.Fournisseur;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.FournisseurRepository;
import SPA.dev.Stock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.Field;


@Service
@RequiredArgsConstructor
public class
FournisseurService {
    private final FournisseurMapper fournisseurMapper;
    private final FournisseurRepository fournisseurRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    public FournisseurDto ajouter(FournisseurDto fournisseurDto) {
        Fournisseur fournisseur = fournisseurMapper.toEntity(fournisseurDto);

        return fournisseurMapper.toDto(fournisseurRepository.save(fournisseur));
    }

    public List<FournisseurDto> liste() {
        int userId=userService.getCurrentUserId();
        int currentUserId =  userService.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(()->new RuntimeException("user not found"));
        User admin = userRepository.findById(user.getCreatedBy())
                .orElseThrow(()->new RuntimeException("not found"));
        List<Fournisseur> fournisseurs = fournisseurRepository.findFournisseursByCreatedBy(userId);
        if (user.getRole()== RoleEnumeration.ADMIN){

            Fournisseur fournisseur = fournisseurRepository.findByTel(admin.getTelephone())
                    .orElseThrow(()->new RuntimeException("fournisseur not found"));
            fournisseurs.add(fournisseur);
        }
        return fournisseurMapper.toDtoList(fournisseurs);
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

    public FournisseurDto modifier1(int id, FournisseurDto fournisseurDto) {
        getFournisseur(id);
        Fournisseur fournisseur = fournisseurMapper.toEntity(fournisseurDto);
        fournisseur.setIdFournissseur(id);
        return fournisseurMapper.toDto(fournisseurRepository.save(fournisseur));
    }



    public FournisseurDto modifier(int id, FournisseurDto fournisseurDto) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(()->new RuntimeException("not found")); // Récupère l'entité existante depuis la base de données
        fournisseur = Fonction.updateEntityWithNonNullFields(fournisseur, fournisseurDto,"idFournissseur"); // Met à jour l'entité avec les champs non nuls du DTO
        return fournisseurMapper.toDto(fournisseurRepository.save(fournisseur));
    }

}
