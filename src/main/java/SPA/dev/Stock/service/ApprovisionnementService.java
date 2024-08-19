package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.ApprovisionnementDto;
import SPA.dev.Stock.mapper.ApprovisionnementMapper;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.repository.ApprovisionnementRepository;
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
    public ApprovisionnementDto ajouter(ApprovisionnementDto approvisionnementDto) {
        approvisionnementDto.setCreatedAt(new Date());
        fournisseurService.getFournisseur(approvisionnementDto.getIdFournisseur());
        entrepotService.getEntrepot(approvisionnementDto.getIdEntrepot());
        Approvisionnement approvisionnement = approvisionnementMapper.toEntity(approvisionnementDto);
        return approvisionnementMapper.toDto(approvisionnementRepository.save(approvisionnement));
    }

    public List<ApprovisionnementDto> liste() {
       List<Approvisionnement> approvisionnements = approvisionnementRepository.findAll();
        return approvisionnementMapper.toDtoList(approvisionnements);
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
        Approvisionnement approvisionnement = approvisionnementMapper.toEntity(approvisionnementDto);
        approvisionnement.setIdApprovisionnement(id);
        return approvisionnementMapper.toDto(approvisionnementRepository.save(approvisionnement));
    }
}
