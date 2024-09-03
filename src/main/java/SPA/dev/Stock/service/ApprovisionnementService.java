package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.ApprovisionnementDto;

import SPA.dev.Stock.dto.ProduitDto;
import SPA.dev.Stock.dto.TransfertDto;
import SPA.dev.Stock.enumeration.EnumTypeMagasin;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.enumeration.StatusTransfertEnum;
import SPA.dev.Stock.mapper.*;
import SPA.dev.Stock.modele.*;

import SPA.dev.Stock.mapper.ApprovisionnementMapper;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Produit;

import SPA.dev.Stock.repository.ApprovisionnementRepository;
import SPA.dev.Stock.repository.MagasinRepository;
import SPA.dev.Stock.repository.ProduitRepository;
import SPA.dev.Stock.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApprovisionnementService {

    private final ApprovisionnementMapper approvisionnementMapper;
    private final FournisseurService fournisseurService;
    private final ApprovisionnementRepository approvisionnementRepository;
    private final UserService userService;
    private final TransfertService transfertService;
    private final FournisseurMapper fournisseurMapper;
    private final ProduitService produitService;
    private final ProduitMapper produitMapper;
    private final UserRepository userRepository;
    private final MagasinRepository magasinRepository;
    private final ProduitRepository produitRepository;
    private final  TransfertMapper transfertMapper;

    @Transactional
    public ApprovisionnementDto ajouter(ApprovisionnementDto approvisionnementDto) {

        // Récupération de l'utilisateur courant
        User user = userRepository.findById(userService.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
          List<TransfertDto> l =  transfertService.getTransfertByMagasin();

        User admin=userRepository.getUsersByRole(RoleEnumeration.SUPER_ADMIN)
                .stream()
                .findFirst()
                .orElseThrow(()->new RuntimeException("admin introuvable"));
        if (user.getId()!= admin.getId()){
        // Vérification si l'approvisionnement est fait directement avec le fournisseur
            if (l!=null) {
                return traiterApprovisionnementAvecTransfert(approvisionnementDto);
            } else {
                    throw new RuntimeException("aucun transfert n est initier pour vous avec ce produit");
            }
        }
        else {
            Magasin mag = admin.getMagasin();
            return traiterApprovisionnementAvecFournisseur(approvisionnementDto,mag);
        }
    }

    private ApprovisionnementDto traiterApprovisionnementAvecFournisseur(ApprovisionnementDto approvisionnementDto, Magasin magasin) {
        Approvisionnement approvisionnement = approvisionnementMapper.toEntity(approvisionnementDto);
        return approvisionnementMapper.toDto(approvisionnementRepository.save(approvisionnement));
    }

    private ApprovisionnementDto traiterApprovisionnementAvecTransfert(ApprovisionnementDto approvisionnementDto) {
        List<TransfertDto> transferts = transfertService.getTransfertByMagasin();


        // Filtre par produit
        TransfertDto transfertParProduit = transferts.stream()
                .filter(trans ->produitRepository.findByDesignation(trans.getProduit()).getIdProduit() ==
                                 Integer.parseInt(approvisionnementDto.getProduit())
                )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aucun transfert trouvé pour le produit spécifié."));

        // Filtre par quantité

        TransfertDto transfertParQuantite = transferts.stream()
                .filter(trans -> trans.getQuantite() >= approvisionnementDto.getQuantite())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Quantité insuffisante pour l'approvisionnement."));

        // Processus de transfert et d'approvisionnement
        int q = transfertParQuantite.getQuantite() - approvisionnementDto.getQuantite();
        transfertParQuantite.setQuantite(q);

        if (transfertParQuantite.getQuantite() == 0) {
            transfertParQuantite.setStatus(StatusTransfertEnum.terminer);
        }

        transfertService.modifier(transfertParQuantite.getIdTransfert(), transfertParQuantite);
        Approvisionnement approvisionnement = approvisionnementMapper.toEntity(approvisionnementDto);
        return approvisionnementMapper.toDto(approvisionnementRepository.save(approvisionnement));
    }




    public List<ApprovisionnementDto> liste() {
        List<Approvisionnement> approvisionnements = approvisionnementRepository.findAll();
        return approvisionnementMapper.toDtoList(approvisionnements);
    }

    public List<ApprovisionnementDto> getApprovisionnementByUser() {

        int userId = userService.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("utilisateur introuvable"));
        List<Approvisionnement> list = approvisionnementRepository.findApprovisionnementByCreatedBy(userId);

        if (user.getRole() != RoleEnumeration.SUPER_ADMIN) {
            User admin = userRepository.getUsersByRole(RoleEnumeration.SUPER_ADMIN)
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Super admin introuvable"));
            list.addAll(approvisionnementRepository.findApprovisionnementByCreatedBy(admin.getId()));
        }
        return approvisionnementMapper.toDtoList(list);
    }

    public List<ApprovisionnementDto> getApprovisionnementByEntrepot(int idEntrepot) {
        Magasin entrepot = magasinRepository.findByIdAndTypeMagasin(idEntrepot, EnumTypeMagasin.ENTREPOT)
                .orElseThrow(()->new RuntimeException("Entrepot not found"));
        int userId = userService.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("utilisateur introuvable"));
        List<ApprovisionnementDto> list =approvisionnementRepository.findApprovisionnementByEntrepotAndCreatedBy(entrepot,userId);
        if (user.getRole()!= RoleEnumeration.SUPER_ADMIN) {
            User admin = userRepository.getUsersByRole(RoleEnumeration.SUPER_ADMIN)
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Super admin introuvable"));
            list.addAll(approvisionnementRepository.findApprovisionnementByEntrepotAndCreatedBy(entrepot,admin.getId()));
        }
        return list;
    }
    public List<ApprovisionnementDto> getApprovisionnementByFournisseur(int idFournisseur) {
        int userId = userService.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("utilisateur introuvable"));
        Fournisseur fournisseur = fournisseurMapper.toEntity(fournisseurService.getFournisseur(idFournisseur).orElseThrow(()->new RuntimeException("fournisseur introuvable")));
        List<ApprovisionnementDto> list =approvisionnementRepository.findApprovisionnementByFournisseurAndCreatedBy(fournisseur,userId);
        if (user.getRole()!= RoleEnumeration.SUPER_ADMIN) {
            User admin = userRepository.getUsersByRole(RoleEnumeration.SUPER_ADMIN)
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Super admin introuvable"));
            list.addAll(approvisionnementRepository.findApprovisionnementByFournisseurAndCreatedBy(fournisseur,admin.getId()));
        }
        return list;
    }

    public List<ApprovisionnementDto> getApprovisionnementByProduit(int idProduit) {
        ProduitDto produitDto = produitService.getProduit(idProduit).orElseThrow(()->new RuntimeException("produit introuvable"));
        Produit produit =produitMapper.toEntity(produitDto);
        return approvisionnementMapper.toDtoList(approvisionnementRepository.findApprovisionnementByProduit(produit));
    }

    public Optional<ApprovisionnementDto> getApprovisionnement(int id) {
        Approvisionnement approvisionnement = approvisionnementRepository.findById(id).orElseThrow(()-> new RuntimeException("approvisionnement not found"));
        return Optional.of(approvisionnementMapper.toDto(approvisionnement));
    }


    public String delete(int id) {
        Approvisionnement approvisionnement = approvisionnementRepository.findById(id).orElseThrow(()-> new RuntimeException("approvisionnement introuvable"));
        approvisionnementRepository.deleteById(id);
        return "suppression effectuer avec succes" ;
    }

    public int getStockDisponible(int produitId) {
        return 0;
    }
}
