package SPA.dev.Stock.service;


import SPA.dev.Stock.dto.CaisseDto;
import SPA.dev.Stock.dto.ClientDto;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.Mapper;
import SPA.dev.Stock.modele.Caisse;
import SPA.dev.Stock.modele.Client;
import SPA.dev.Stock.repository.CaisseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CaisseService {
    private final CaisseRepository caisseRepository;
    private final Mapper mapper;

    public List<CaisseDto> getAll() {
        return caisseRepository.findAll()
                .stream()
                .map(mapper::toCaisseDto)
                .collect(Collectors.toList());
    }


    public CaisseDto getCaisse(Long id) {
        return caisseRepository.findById(id)
                .map(mapper::toCaisseDto)
                .orElseThrow(() -> new AppException("Caisse not found", HttpStatus.NOT_FOUND));
    }


    public CaisseDto addCaisse(CaisseDto caisseDto) {
        Caisse caisse = mapper.toCaisseEntity(caisseDto);
        Caisse savedCaisse = caisseRepository.save(caisse);
        return mapper.toCaisseDto(savedCaisse);
    }


    public CaisseDto removeCaisse(Long id) {
        Caisse caisse = caisseRepository.findById(id)
                .orElseThrow(() -> new AppException("Caisse not found", HttpStatus.NOT_FOUND));
        caisseRepository.deleteById(id);
        return mapper.toCaisseDto(caisse);
    }


    public CaisseDto updateCaisse(Long id, CaisseDto caisseDto) {
        Caisse caisse = caisseRepository.findById(id)
                .orElseThrow(() -> new AppException("Caisse not found", HttpStatus.NOT_FOUND));

        caisse.setTypePaiement(caisseDto.getTypePaiement());
        caisse.setTypeOperation(caisseDto.getTypeOperation());
        caisse.setMontant(caisseDto.getMontant());
        caisse.setMotif(caisseDto.getMotif());

        Caisse updatedCaisse = caisseRepository.save(caisse);
        return mapper.toCaisseDto(updatedCaisse);
    }

}
