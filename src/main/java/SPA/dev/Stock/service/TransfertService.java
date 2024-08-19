package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.TransfertDto;
import SPA.dev.Stock.mapper.TransfertMapper;
import SPA.dev.Stock.modele.Transfert;
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
    private final TransfertMapper transfertMapper;
    public TransfertDto ajouter(TransfertDto transfertDto) {
        magasinService.getMagasin(transfertDto.getIdMagasin());
        produitService.getProduit(transfertDto.getIdProduit());
        Transfert transfert = transfertMapper.toEntity(transfertDto);

        return transfertMapper.toDto(transfertRepository.save(transfert));
    }

    public List<TransfertDto> liste() {
        return transfertMapper.toDtoList(transfertRepository.findAll());
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
        Transfert transfert = transfertMapper.toEntity(transfertDto);
        getTransfert(id);
        magasinService.getMagasin(transfertDto.getIdMagasin());
        produitService.getProduit(transfertDto.getIdProduit());
        transfert.setIdTransfert(id);
        return transfertMapper.toDto(transfertRepository.save(transfert));
    }
}
