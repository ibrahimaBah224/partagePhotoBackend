package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.VenteInitDto;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.VenteInitMapper;
import SPA.dev.Stock.modele.VenteInit;
import SPA.dev.Stock.repository.VenteInitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VenteInitService {
    private final VenteInitRepository venteInitRepository;
    private final VenteInitMapper venteInitMapper;

    public List<VenteInitDto> getAll(){
        List<VenteInit> ventes = venteInitRepository.findAll();
        return venteInitMapper.toVenteInitDtoList(ventes);
    }

    public VenteInitDto getVenteInit(Long id){
        VenteInit vente = venteInitRepository.findById(id)
                .orElseThrow(()-> new AppException("Vente initial not found", HttpStatus.NOT_FOUND));
        return venteInitMapper.toVenteInitDto(vente);
    }

    public VenteInitDto addVenteInit(VenteInitDto venteInitDto){
        VenteInit venteInit = venteInitMapper.toVenteInit(venteInitDto);
        venteInit.setReference(UUID.randomUUID().toString());
        VenteInit newVenteInit = venteInitRepository.save(venteInit);
        return venteInitMapper.toVenteInitDto(newVenteInit);
    }

    public VenteInitDto removeVenteInit(Long id){
        VenteInit venteInit = venteInitRepository.findById(id)
                .orElseThrow(()-> new AppException("Vente not found", HttpStatus.NOT_FOUND));
        venteInitRepository.deleteById(id);
        return venteInitMapper.toVenteInitDto(venteInit);
    }

    public VenteInitDto updateVenteInit(Long id, VenteInitDto venteInitDto){
        VenteInit venteInit = venteInitRepository.findById(id)
                .orElseThrow(()-> new AppException("Vente not found", HttpStatus.NOT_FOUND));
        venteInit.setClient(venteInitDto.getClient());
        venteInit.setStatus(venteInitDto.getStatus());
        venteInit.setEtatCommande(venteInitDto.getEtatCommande());
        venteInit.setRemise(venteInitDto.getRemise());
        return venteInitMapper.toVenteInitDto(venteInitRepository.save(venteInit));
    }




}
