package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.MagasinDto;
import SPA.dev.Stock.dto.ProduitDto;
import SPA.dev.Stock.dto.TransfertDto;
import SPA.dev.Stock.enumeration.RoleEnumeration;
import SPA.dev.Stock.enumeration.StatusTransfertEnum;
import SPA.dev.Stock.mapper.MagasinMapper;
import SPA.dev.Stock.mapper.ProduitMapper;
import SPA.dev.Stock.mapper.SousCategorieMapper;
import SPA.dev.Stock.mapper.TransfertMapper;
import SPA.dev.Stock.modele.*;
import SPA.dev.Stock.repository.TransfertRepository;
import SPA.dev.Stock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransfertService {
    private final TransfertRepository transfertRepository;
    private final ProduitService produitService;
    private final MagasinService magasinService;
    private final MagasinMapper magasinMapper;
    private final TransfertMapper transfertMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    public TransfertDto ajouter(TransfertDto transfertDto) {
        Transfert transfert = transfertMapper.toEntity(transfertDto);
        transfert.setStatus(StatusTransfertEnum.en_cours);
        Produit produit = transfert.getProduit();
        if(produit.getStatut()==0){

           throw new RuntimeException("le produit que vous voulez transferer n'est pas valider");
        }
        else {
            transfertRepository.save(transfert);
            return transfertMapper.toDto(transfert);
        }
    }

    public List<TransfertDto> liste() {
        User admin=userRepository.getUsersByRole(RoleEnumeration.SUPER_ADMIN)
                .stream()
                .findFirst()
                .orElseThrow(()->new RuntimeException("admin introuvable"));
        if (userService.getCurrentUserId()==admin.getId()){
            return transfertMapper.toDtoList(transfertRepository.findAll());
        }
        else {
            Magasin magasin = magasinMapper.magasinDTOToMagasin(magasinService.getMagasinsForCurrentUser());
            List<TransfertDto> list = transfertRepository.findTransfertsByMagasin(magasin);

            return list;
        }
    }

    public List<TransfertDto> getTransfertByMagasin() {
        Magasin magasin =magasinMapper.magasinDTOToMagasin(magasinService.getMagasinsForCurrentUser());
        return transfertMapper.toDtoList(transfertRepository.findTransfertsByMagasinAndStatus(magasin,StatusTransfertEnum.en_cours));
    }

    public Optional<TransfertDto> getTransfert(int id) {
        Transfert transfert = transfertRepository.findById(id).orElseThrow(()-> new RuntimeException("transfert introuvable"));
        return Optional.of(transfertMapper.toDto(transfert));
    }

    public String delete(int id) {
        getTransfert(id);
        transfertRepository.findById(id);
        return "supprimer avec success";
    }

    public TransfertDto modifier(int id, TransfertDto transfertDto) {

        getTransfert(id);
        User admin=userRepository.getUsersByRole(RoleEnumeration.SUPER_ADMIN)
                .stream()
                .findFirst()
                .orElseThrow(()->new RuntimeException("admin introuvable"));
        if (userService.getCurrentUserId()==admin.getId()) {
            Transfert transfert = transfertMapper.toEntity(transfertDto);
            transfert.setIdTransfert(id);
            return transfertMapper.toDto(transfertRepository.save(transfert));
        }
        else {
            throw new RuntimeException("vous ne pouvez pas modifier un transfert car vous n avez pas le role necessaire");
        }
    }
}
