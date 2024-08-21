package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.ApprovisionnementDto;

import SPA.dev.Stock.dto.TransfertDto;
import SPA.dev.Stock.enumeration.StatusTransfertEnum;
import SPA.dev.Stock.mapper.*;
import SPA.dev.Stock.modele.*;

import SPA.dev.Stock.mapper.ApprovisionnementMapper;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Produit;

import SPA.dev.Stock.repository.ApprovisionnementRepository;
import SPA.dev.Stock.repository.MagasinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApprovisionnementService {

    private final ApprovisionnementMapper approvisionnementMapper;
    private final EntrepotService entrepotService;
    private final FournisseurService fournisseurService;
    private final ApprovisionnementRepository approvisionnementRepository;
    private final UserService userService;
    private final MagasinRepository magasinRepository;
    private final TransfertService transfertService;
    private final EntrepotMapper entrepotMapper;
    private final FournisseurMapper fournisseurMapper;
    private final TransfertMapper transfertMapper;
    private final ProduitService produitService;
    private final ProduitMapper produitMapper;
    private final MagasinService magasinService;
    private final MagasinMapper magasinMapper;
    public ApprovisionnementDto ajouter(ApprovisionnementDto approvisionnementDto) {

        User user =  userService.findById(userService.getCurrentUserId()).orElseThrow(()->new RuntimeException("utilisateur introuvable"));
        Magasin magasin =magasinRepository.findByUserId(user.getId());
        List<TransfertDto> transfert = transfertService.getTransfertByMagasin(magasin.getId());

        if (transfert!=null) {
           for (TransfertDto trans : transfert){
               if (trans.getIdProduit()==approvisionnementDto.getIdProduit()){
                   approvisionnementDto.setCreatedBy(user.getId());
                   Fournisseur fournisseur =fournisseurMapper.toEntity(fournisseurService.getFournisseur(approvisionnementDto.getIdFournisseur()).orElseThrow(()->new RuntimeException("fourniseur introuvable")));
                   Entrepot entrepot =entrepotMapper.toEntity(entrepotService.getEntrepot(approvisionnementDto.getIdEntrepot()).orElseThrow(()->new RuntimeException("entrepot introuvable")));
                   Produit produit = produitMapper.toEntity(produitService.getProduit(trans.getIdProduit()).orElseThrow(()->new RuntimeException("produit introuvable")));
                   Transfert transfert1 = transfertMapper.toEntity(trans,produit,magasin);
                   Approvisionnement approvisionnement = approvisionnementMapper.toEntity(approvisionnementDto,transfert1.getProduit(),entrepot,fournisseur);
                   trans.setStatus(StatusTransfertEnum.terminer);
                   transfertService.modifier(trans.getIdTransfert(),trans);
                   return approvisionnementMapper.toDto(approvisionnementRepository.save(approvisionnement));
               }
           }
        }
         return null;
    }

    public List<ApprovisionnementDto> liste() {
       List<Approvisionnement> approvisionnements = approvisionnementRepository.findAll();
        return approvisionnementMapper.toDtoList(approvisionnements);
    }

    public List<ApprovisionnementDto> getApprovisionnementByUser() {
        List<Approvisionnement> approvisionnements = approvisionnementRepository.findApprovisionnementByCreatedBy(userService.getCurrentUserId());
        return approvisionnementMapper.toDtoList(approvisionnements);
    }
/*
    public List<ApprovisionnementDto> getApprovisionnementByEntrepot(int idEntrepot) {
        List<Approvisionnement> approvisionnements = approvisionnementRepository.findApprovisionnementByEntrepot(idEntrepot);
        return approvisionnementMapper.toDtoList(approvisionnements);
    }
    public List<ApprovisionnementDto> getApprovisionnementBFournisseur(int idFournisseur) {
        List<Approvisionnement> approvisionnements = approvisionnementRepository.findApprovisionnementByFournisseur(idFournisseur);
        return approvisionnementMapper.toDtoList(approvisionnements);
    }

    public List<ApprovisionnementDto> getApprovisionnementByProduit(int idProduit) {
        List<Approvisionnement> approvisionnements = approvisionnementRepository.findApprovisionnementByProduit(idProduit);
        return approvisionnementMapper.toDtoList(approvisionnements);
    }
*/
    public Optional<ApprovisionnementDto> getApprovisionnement(int id) {
        Approvisionnement approvisionnement = approvisionnementRepository.findById(id).orElseThrow(()-> new RuntimeException("approvisionnement not found"));
        return Optional.of(approvisionnementMapper.toDto(approvisionnement));
    }


    public String delete(int id) {
        Approvisionnement approvisionnement = approvisionnementRepository.findById(id).orElseThrow(()-> new RuntimeException("approvisionnement introuvable"));
        approvisionnementRepository.deleteById(approvisionnement.getIdApprovisionnement());
        return "suppression effectuer avec succes" ;
    }

    public ApprovisionnementDto modifier(int id, ApprovisionnementDto approvisionnementDto) {
        getApprovisionnement(id);
        Fournisseur fournisseur =fournisseurMapper.toEntity(fournisseurService.getFournisseur(approvisionnementDto.getIdFournisseur()).orElseThrow(()->new RuntimeException("fourniseur introuvable")));
        Entrepot entrepot =entrepotMapper.toEntity(entrepotService.getEntrepot(approvisionnementDto.getIdEntrepot()).orElseThrow(()->new RuntimeException("entrepot introuvable")));
        Produit produit = produitMapper.toEntity(produitService.getProduit(approvisionnementDto.getIdProduit()).orElseThrow(()->new RuntimeException("produit introuvable")));
        approvisionnementDto.setUpdatedBy(userService.getCurrentUserId());

        Approvisionnement approvisionnement = approvisionnementMapper.toEntity(approvisionnementDto,produit,entrepot,fournisseur);
        approvisionnement.setIdApprovisionnement(id);
        return approvisionnementMapper.toDto(approvisionnementRepository.save(approvisionnement));
    }
    public int getStockDisponible(int produitId) {
        return 0;
    }
}
