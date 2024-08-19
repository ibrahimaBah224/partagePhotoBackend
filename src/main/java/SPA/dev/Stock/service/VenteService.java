package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.VenteDto;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.VenteMapper;
import SPA.dev.Stock.modele.Vente;
import SPA.dev.Stock.repository.VenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenteService {
    private final VenteRepository venteRepository;
    private final VenteMapper venteMapper;

    public VenteService(VenteRepository venteRepository, VenteMapper venteMapper) {
        this.venteRepository = venteRepository;
        this.venteMapper = venteMapper;
    }

    public List<VenteDto> getAll() {
        List<Vente> ventes = venteRepository.findAll();
        return venteMapper.toVenteDtoList(ventes);
    }

    public VenteDto getVente(Long id) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new AppException("Vente not found", HttpStatus.NOT_FOUND));
        return venteMapper.toVenteDto(vente);
    }

    public List<VenteDto> getVenteByProduit(int id) {
        List<Vente> ventes = venteRepository.findByProduitIdProduit(id);
        if (ventes.isEmpty()) {
            throw new AppException("pas de vente lié à ce produit", HttpStatus.NOT_FOUND);
        }
        return venteMapper.toVenteDtoList(ventes);
    }

    public VenteDto addVente(VenteDto venteDto) {
        Vente vente = venteMapper.toVente(venteDto);
        Vente newVente = venteRepository.save(vente);
        return venteMapper.toVenteDto(newVente);
    }

    public VenteDto removeVente(Long id) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new AppException("Vente not found", HttpStatus.NOT_FOUND));
        venteRepository.deleteById(id);
        return venteMapper.toVenteDto(vente);
    }

    public VenteDto updateVente(Long id, VenteDto venteDto) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new AppException("Vente not found", HttpStatus.NOT_FOUND));
        vente.setProduit(venteDto.getProduit());
        vente.setVenteInit(venteDto.getVenteInit());
        vente.setQuantite(venteDto.getQuantite());
        vente.setPrixVente(venteDto.getPrixVente());
        return venteMapper.toVenteDto(venteRepository.save(vente));
    }
}

