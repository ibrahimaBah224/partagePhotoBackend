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
    private final TransfertMapper transfertMapper;
    private final FournisseurMapper fournisseurMapper;
    private final ProduitService produitService;
    private final ProduitMapper produitMapper;
    private final UserRepository userRepository;
    private final MagasinRepository magasinRepository;

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
        List<TransfertDto> listTransferts =transfertService.getTransfertByMagasin();
        List<Transfert> transferts = transfertMapper.toEntityList(listTransferts);
        // Filtrer les transferts par produit
        Transfert transfertCorrespondant = transferts.stream()
                .filter(trans -> {
                    if (!(trans.getProduit().getIdProduit() == Integer.valueOf(approvisionnementDto.getProduit()))) {
                        return false;
                    }
                    return true;
                })
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Aucun transfert avec le produit spécifié trouvé."));

        // Vérifier la quantité disponible
        if (transfertCorrespondant.getQuantite() < approvisionnementDto.getQuantite()) {
            throw new RuntimeException("Quantité insuffisante pour l'approvisionnement.");
        }

        // Mettre à jour la quantité et le statut du transfert
        TransfertDto t = transfertMapper.toDto(transfertCorrespondant);
        t.setProduit(String.valueOf(transfertCorrespondant.getProduit().getIdProduit()));
        t.setMagasin(String.valueOf(transfertCorrespondant.getMagasin().getId()));
        int q = t.getQuantiteRestante() - approvisionnementDto.getQuantite();
        t.setQuantiteRestante(q);
        if (t.getQuantiteRestante() == 0) {
            t.setStatus(StatusTransfertEnum.terminer);
        }
        // Modifier le transfert
        transfertService.modifier(transfertCorrespondant.getIdTransfert(), t);

        // Préparer et sauvegarder l'approvisionnement
        Approvisionnement approvisionnement = approvisionnementMapper.toEntity(approvisionnementDto);
        return approvisionnementMapper.toDto(approvisionnementRepository.save(approvisionnement));
    }






    public List<ApprovisionnementDto> liste() {
        List<Approvisionnement> approvisionnements = approvisionnementRepository.findAllByCreatedBy(userService.getCurrentUserId());
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
