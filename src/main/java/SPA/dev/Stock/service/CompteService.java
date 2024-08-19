package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.CompteDto;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.CompteMapper;
import SPA.dev.Stock.modele.Compte;
import SPA.dev.Stock.repository.CompteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompteService {
    private final CompteRepository compteRepository;
    private final CompteMapper compteMapper;

    public List<Compte> getAll(){
        List<Compte> comptes = compteRepository.findAll();
        return comptes;
    }

    public CompteDto getCompte(Long id){
        Compte compte = compteRepository.findById(id)
                .orElseThrow(()-> new AppException("Compte not found", HttpStatus.NOT_FOUND));
        return compteMapper.toCompteDto(compte);
    }

    public CompteDto addCompte(CompteDto compteDto){
        Compte compte = compteMapper.toCompte(compteDto);
        compte.setReference(UUID.randomUUID().toString());
        Compte newCompte = compteRepository.save(compte);
        return compteMapper.toCompteDto(newCompte);
    }

    public CompteDto removeCompte(Long id){
        Compte compte = compteRepository.findById(id)
                .orElseThrow(()-> new AppException("Compte not found", HttpStatus.NOT_FOUND));
        compteRepository.deleteById(id);
        return compteMapper.toCompteDto(compte);
    }

    public CompteDto updateCompte(Long id, CompteDto compteDto){
        Compte compte = compteRepository.findById(id)
                .orElseThrow(()-> new AppException("Compte not found", HttpStatus.NOT_FOUND));
        compte.setNumero(compteDto.getNumero());
        compte.setApiKey(compteDto.getApiKey());
        compte.setDescription(compteDto.getDescription());
        return compteMapper.toCompteDto(compteRepository.save(compte));
    }
}