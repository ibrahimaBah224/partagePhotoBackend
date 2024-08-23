package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.PerteDto;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.Mapper;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Perte;
import SPA.dev.Stock.repository.ApprovisionnementRepository;
import SPA.dev.Stock.repository.PerteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerteService {
    private final ApprovisionnementRepository approvisionnementRepository;
    private final PerteRepository perteRepository;
    private final Mapper perteMapper; // Injection de Mapper
    private final UserService userService;

    private List<PerteDto> getAll() {
        return perteRepository.findAllByCreatedBy(userService.getCurrentUserId())
                .stream()
                .map(perteMapper::toPerteDto)
                .collect(Collectors.toList());
    }

    private PerteDto getPerte(Long id) {
        Perte perte = perteRepository.findById(id)
                .orElseThrow(()-> new AppException("pas de perte avec ce parametre", HttpStatus.NOT_FOUND));
        return perteMapper.toPerteDto(perte);
    }

    private PerteDto addPerte(PerteDto perteDto){
        Approvisionnement approvisionnement = approvisionnementRepository.findById(perteDto.getIdApprovisionnement())
                .orElseThrow(()-> new AppException("approvisionnement non disponible", HttpStatus.NOT_FOUND));
        Perte perte = perteMapper.toPerteEntity(perteDto,  approvisionnement);
        Perte newPerte = perteRepository.save(perte);
        return perteMapper.toPerteDto(newPerte);
    }

    private PerteDto removePerte(Long id){
        Perte perte= perteRepository.findById(id)
                .orElseThrow(()->new AppException("cette perte n'est pas disponible", HttpStatus.NOT_FOUND));
        perteRepository.deleteById(id);
        return  perteMapper.toPerteDto(perte);
    }
}
