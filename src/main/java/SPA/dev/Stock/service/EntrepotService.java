package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.EntrepotDto;
import SPA.dev.Stock.mapper.EntrepotMapper;
import SPA.dev.Stock.modele.Entrepot;
import SPA.dev.Stock.repository.EntrepotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntrepotService {
    private final EntrepotRepository entrepotRepository;
    private final EntrepotMapper entrepotMapper;
    private final UserService userService;
    public EntrepotDto ajouter(EntrepotDto entrepotDto) {
        entrepotDto.setCreatedBy(userService.getCurrentUserId());
        Entrepot entrepot = entrepotMapper.toEntity(entrepotDto);
        return entrepotMapper.toDto(entrepotRepository.save(entrepot));
    }

    public List<EntrepotDto> liste() {
        return entrepotMapper.toDtoList(entrepotRepository.findAll());
    }

    public Optional<EntrepotDto> getEntrepot(int id) {
        Entrepot entrepot = entrepotRepository.findById(id).orElseThrow(()-> new RuntimeException("entrepot introuvable"));
        return Optional.of(entrepotMapper.toDto(entrepot));
    }

    public String delete(int id) {
        getEntrepot(id);
        entrepotRepository.deleteById(id);
        return "suppression effectuer avec success";
    }

    public EntrepotDto modifier(int id, EntrepotDto entrepotDto) {
        getEntrepot(id);
        entrepotDto.setUpdatedBy(userService.getCurrentUserId());
        Entrepot entrepot = entrepotMapper.toEntity(entrepotDto);
        entrepot.setIdEntrepot(id);
        return entrepotMapper.toDto(entrepotRepository.save(entrepot));
    }
}
