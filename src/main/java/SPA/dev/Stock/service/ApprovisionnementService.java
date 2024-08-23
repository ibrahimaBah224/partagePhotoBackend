package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.ApprovisionnementDto;

import SPA.dev.Stock.dto.ProduitDto;
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
import org.springframework.stereotype.Service;


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
    private  final SousCategorieService sousCategorieService;
    private final SousCategorieMapper sousCategorieMapper;


    private final MagasinService magasinService;
    private final MagasinMapper magasinMapper;
    public ApprovisionnementDto ajouter(ApprovisionnementDto approvisionnementDto) {

        User user =  userService.findById(userService.getCurrentUserId()).orElseThrow(()->new RuntimeException("utilisateur introuvable"));
        Magasin magasin =magasinRepository.findByUserId(user.getId());
        Entrepot entrepot =entrepotMapper.toEntity(entrepotService.getEntrepot(approvisionnementDto.getIdEntrepot()).orElseThrow(()->new RuntimeException("entrepot introuvable")));
        Fournisseur f = fournisseurMapper.toEntity(fournisseurService.getFournisseur(approvisionnementDto.getIdFournisseur()).orElseThrow(()->new RuntimeException("fournisseur introuvable")));
        //on verrifie si l'utilisateur veux s'approvisionner avec un de ses fournisseurs sans que le magasin principal initieun transfert
        if (approvisionnementDto.getIdFournisseur()==f.getIdFournissseur()){
            TransfertDto transfertDto = new TransfertDto();
            transfertDto.setIdMagasin(magasin.getId());
            transfertDto.setIdProduit(approvisionnementDto.getIdProduit());
            transfertDto.setStatus(StatusTransfertEnum.terminer);
            ProduitDto produitDto = produitService.getProduit(approvisionnementDto.getIdProduit()).orElseThrow(()->new RuntimeException("produit introuvable"));
            SousCategorie sousCategorie=sousCategorieMapper.toEntity(sousCategorieService.getSousCategorie(produitDto.getId_sousCategorie()).orElseThrow(()->new RuntimeException("sous categorie introuvable"))) ;
            Produit produit = produitMapper.toEntity(produitDto,sousCategorie);
            transfertMapper.toEntity(transfertService.ajouter(transfertDto),produit,magasin);
            Approvisionnement approvisionnement = approvisionnementMapper.toEntity(approvisionnementDto,produit,entrepot,f);
            return approvisionnementMapper.toDto(approvisionnementRepository.save(approvisionnement));
        }
        else{
            // on recupere la liste de tous les transferts consernant l'utilisateur connecte
            List<TransfertDto> transfert = transfertService.getTransfertByMagasin(magasin.getId());
            if (transfert!=null) {
                for (TransfertDto trans : transfert){
                    //on verrifie si le prpduit qu'on  veux approvisionner est bien celui qui a ete initier dans transfert
                    if (trans.getIdProduit()==approvisionnementDto.getIdProduit()){
                        approvisionnementDto.setCreatedBy(user.getId());
                        ProduitDto produitDto = produitService.getProduit(trans.getIdProduit()).orElseThrow(()->new RuntimeException("produit introuvable"));
                        SousCategorie sousCategorie=sousCategorieMapper.toEntity(sousCategorieService.getSousCategorie(produitDto.getId_sousCategorie()).orElseThrow(()->new RuntimeException("sous categorie introuvable"))) ;

                        Produit produit = produitMapper.toEntity(produitService.getProduit(trans.getIdProduit()).orElseThrow(()->new RuntimeException("produit introuvable")),sousCategorie);
                        Transfert transfert1 = transfertMapper.toEntity(trans,produit,magasin);
                        Approvisionnement approvisionnement = approvisionnementMapper.toEntity(approvisionnementDto,transfert1.getProduit(),entrepot,f);
                        trans.setStatus(StatusTransfertEnum.terminer);
                        transfertService.modifier(trans.getIdTransfert(),trans);
                        return approvisionnementMapper.toDto(approvisionnementRepository.save(approvisionnement));
                    }
                    else return null;
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

    public List<ApprovisionnementDto> getApprovisionnementByEntrepot(int idEntrepot) {
        Entrepot entrepot =entrepotMapper.toEntity(entrepotService.getEntrepot(idEntrepot).orElseThrow(()->new RuntimeException("entrepot introuvable")));
        return approvisionnementRepository.findApprovisionnementByEntrepot(entrepot);
    }
    public List<ApprovisionnementDto> getApprovisionnementByFournisseur(int idFournisseur) {
        Fournisseur fournisseur = fournisseurMapper.toEntity(fournisseurService.getFournisseur(idFournisseur).orElseThrow(()->new RuntimeException("fournisseur introuvable")));
        return approvisionnementRepository.findApprovisionnementByFournisseur(fournisseur);
    }

    public List<ApprovisionnementDto> getApprovisionnementByProduit(int idProduit) {
        ProduitDto produitDto = produitService.getProduit(idProduit).orElseThrow(()->new RuntimeException("produit introuvable"));
        SousCategorie sousCategorie=sousCategorieMapper.toEntity(sousCategorieService.getSousCategorie(produitDto.getId_sousCategorie()).orElseThrow(()->new RuntimeException("sous categorie introuvable"))) ;
        Produit produit =produitMapper.toEntity(produitDto,sousCategorie);
        return  approvisionnementRepository.findApprovisionnementByProduit(produit);
    }

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
        ProduitDto produitDto = produitService.getProduit(approvisionnementDto.getIdProduit()).orElseThrow(()->new RuntimeException("produit introuvable"));
        SousCategorie sousCategorie=sousCategorieMapper.toEntity(sousCategorieService.getSousCategorie(produitDto.getId_sousCategorie()).orElseThrow(()->new RuntimeException("sous categorie introuvable"))) ;
        Produit produit = produitMapper.toEntity(produitDto,sousCategorie);
        approvisionnementDto.setUpdatedBy(userService.getCurrentUserId());

        Approvisionnement approvisionnement = approvisionnementMapper.toEntity(approvisionnementDto,produit,entrepot,fournisseur);
        approvisionnement.setIdApprovisionnement(id);
        return approvisionnementMapper.toDto(approvisionnementRepository.save(approvisionnement));
    }

    public int getStockDisponible(int produitId) {
        return 0;
    }
}
