package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.PerteDto;
import SPA.dev.Stock.dto.RecyclageDto;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.Mapper;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Perte;
import SPA.dev.Stock.modele.Recyclage;
import SPA.dev.Stock.repository.PerteRepository;
import SPA.dev.Stock.repository.RecyclageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecyclageService {
    private final RecyclageRepository recyclageRepository;
    private final PerteService perteService;
    private final UserService userService;
    private  final Mapper recyclageMapper;
    private final PerteRepository perteRepository;

    public List<RecyclageDto> getAll() {
        return recyclageRepository.findAllByCreatedBy(userService.getCurrentUserId())
                .stream()
                .map(recyclageMapper::toRecyclageDto)
                .collect(Collectors.toList());
    }

    public RecyclageDto getPerte(Long id) {
        Recyclage recyclage = recyclageRepository.findById(id)
                .orElseThrow(()-> new AppException("pas de recyclage avec cette perte", HttpStatus.NOT_FOUND));
        return recyclageMapper.toRecyclageDto(recyclage);
    }

    public RecyclageDto addRecyclage(RecyclageDto recyclageDto){
        Perte perte = perteRepository.findById(recyclageDto.getIdPerte())
                .orElseThrow(()-> new AppException("cette perte n'est pas disponible", HttpStatus.NOT_FOUND));
        Recyclage recyclage = recyclageMapper.toRecyclageEntity( recyclageDto , perte);
        recyclageRepository.save(recyclage);
        return recyclageMapper.toRecyclageDto(recyclage);
    }

    public RecyclageDto removeRecyclage(Long id){
        Recyclage recyclage= recyclageRepository.findById(id)
                .orElseThrow(()->new AppException("recyclage n'est pas disponible", HttpStatus.NOT_FOUND));
        recyclageRepository.deleteById(id);
        return  recyclageMapper.toRecyclageDto(recyclage);
    }

    public RecyclageDto updateRecyclage(Long id, RecyclageDto recyclageDto){
        Recyclage recyclage= recyclageRepository.findById(id)
                .orElseThrow(()-> new AppException("recyclage n'est pas disponible", HttpStatus.NOT_FOUND));
        Perte perte= perteRepository.findById(recyclageDto.getIdPerte())
                .orElseThrow(()-> new AppException("cette perte n'est pas disponible", HttpStatus.NOT_FOUND));
        recyclage.setPerte(perte);
        recyclage.setQuantitePerdu(recyclageDto.getQuantitePerdu());
        recyclage.setStatus(recyclageDto.getStatus());
        return recyclageMapper.toRecyclageDto(recyclage);
    }
}
