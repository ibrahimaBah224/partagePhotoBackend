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

    public TransfertDto ajouter(TransfertDto transfertDto) {
        Transfert transfert = transfertMapper.toEntity(transfertDto);
        transfert.setStatus(StatusTransfertEnum.en_cours);
        transfertRepository.save(transfert);
        return transfertMapper.toDto(transfert);
    }

    public List<TransfertDto> liste() {
        Magasin magasin =magasinMapper.magasinDTOToMagasin(magasinService.getMagasinsForCurrentUser());
        List<TransfertDto> list = transfertRepository.findTransfertsByMagasin(magasin);

        return list;
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
        transfertDto.setUpdatedBy(userService.getCurrentUserId());
        Transfert transfert = transfertMapper.toEntity(transfertDto);
        transfert.setIdTransfert(id);
        return transfertMapper.toDto(transfertRepository.save(transfert));
    }
}
