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
        int userId= userService.getCurrentUserId();
        return entrepotMapper.toDtoList(entrepotRepository.findEntrepotsByCreatedBy(userId));
    }

    public Optional<EntrepotDto> getEntrepot(int id) {
        int userId = userService.getCurrentUserId();
        Entrepot entrepot = entrepotRepository.findEntrepotByIdEntrepotAndCreatedBy(id, userId).orElseThrow(()-> new RuntimeException("entrepot introuvable"));
        return Optional.of(entrepotMapper.toDto(entrepot));
    }

    public String delete(int id) {
        getEntrepot(id);
        entrepotRepository.deleteById(id);
        return "suppression effectuer avec success";
    }

    public EntrepotDto modifier(int id, EntrepotDto entrepotDto) {
        getEntrepot(id);
        Entrepot entrepot = entrepotMapper.toEntity(entrepotDto);
        entrepot.setIdEntrepot(id);
        return entrepotMapper.toDto(entrepotRepository.save(entrepot));
    }
}
