package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.VenteDto;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.Mapper;
import SPA.dev.Stock.modele.Approvisionnement;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.modele.Vente;
import SPA.dev.Stock.modele.VenteInit;
import SPA.dev.Stock.repository.ApprovisionnementRepository;
import SPA.dev.Stock.repository.ProduitRepository;
import SPA.dev.Stock.repository.VenteInitRepository;
import SPA.dev.Stock.repository.VenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VenteService {

    private final VenteRepository venteRepository;
    private final Mapper venteMapper;
    private final UserService userService;
    private final VenteInitRepository venteInitRepository;
    private  final ProduitRepository produitRepository;
    private final ApprovisionnementService approvisionnementService;
    public List<VenteDto> getAll() {
        Integer userId = userService.getCurrentUserId();
        List<Vente> ventes = venteRepository.findByCreatedBy(userId);
        return ventes.stream()
                .map(venteMapper::toVenteDto)
                .collect(Collectors.toList());
    }

    public VenteDto getVente(Long id) {
        Vente vente = venteRepository.findByIdAndCreatedBy(id, userService.getCurrentUserId())
                .orElseThrow(() -> new AppException("Vente not found", HttpStatus.NOT_FOUND));
        return venteMapper.toVenteDto(vente);
    }

   /*public List<VenteDto> getVenteByProduit(int produitId) {
        Produit produit = approvisionnementService.getApprovisionnementByProduit(produitId);
        List<Vente> ventes = venteRepository.(produitId, userService.getCurrentUserId());
        if (ventes.isEmpty()) {
            throw new AppException("Pas de vente liée à ce produit", HttpStatus.NOT_FOUND);
        }
        return ventes.stream()
                .map(venteMapper::toVenteDto)
                .collect(Collectors.toList());
    }*/

    public VenteDto addVente(VenteDto venteDto) {
        Produit produit = produitRepository.findById(venteDto.getIdProduit())
                .orElseThrow(() -> new AppException("Produit not found", HttpStatus.NOT_FOUND));
        VenteInit venteInit = venteInitRepository.findById(venteDto.getVenteInitId())
                .orElseThrow(() -> new AppException("VenteInit not found", HttpStatus.NOT_FOUND));

        Vente vente = venteMapper.toVenteEntity(venteDto, produit, venteInit);
        vente.setCreatedBy(userService.getCurrentUserId());
        Vente newVente = venteRepository.save(vente);
        return venteMapper.toVenteDto(newVente);
    }

    public VenteDto removeVente(Long id) {
        Vente vente = venteRepository.findByIdAndCreatedBy(id, userService.getCurrentUserId())
                .orElseThrow(() -> new AppException("Vente not found", HttpStatus.NOT_FOUND));
        venteRepository.deleteById(id);
        return venteMapper.toVenteDto(vente);
    }

    public VenteDto updateVente(Long id, VenteDto venteDto) {
        Vente vente = venteRepository.findByIdAndCreatedBy(id, userService.getCurrentUserId())
                .orElseThrow(() -> new AppException("Vente not found", HttpStatus.NOT_FOUND));

        Produit produit = produitRepository.findById(venteDto.getIdProduit())
                .orElseThrow(() -> new AppException("Produit not found", HttpStatus.NOT_FOUND));
        VenteInit venteInit = venteInitRepository.findById(venteDto.getVenteInitId())
                .orElseThrow(() -> new AppException("VenteInit not found", HttpStatus.NOT_FOUND));

        vente.setProduit(produit);
        vente.setVenteInit(venteInit);
        vente.setQuantite(venteDto.getQuantite());
        vente.setPrixVente(venteDto.getPrixVente());

        return venteMapper.toVenteDto(venteRepository.save(vente));
    }
  /*  public List<VenteDto> getVentesByDate(Date date) {
        Integer userId = userService.getCurrentUserId();
        List<Vente> ventes = venteRepository.findByCreatedAtAndCreatedBy(date, userId);
        if (ventes.isEmpty()) {
            throw new AppException("Aucune vente trouvée pour cette date", HttpStatus.NOT_FOUND);
        }
        return ventes.stream()
                .map(venteMapper::toVenteDto)
                .collect(Collectors.toList());
    }*/



  /*  public List<VenteDto> getVentesByDate(Date date) {
        Integer userId = userService.getCurrentUserId();
        List<Vente> ventes = venteRepository.findByCreatedAtAndCreatedBy(date, userId);
        if (ventes.isEmpty()) {
            throw new AppException("Aucune vente trouvée pour cette date", HttpStatus.NOT_FOUND);
        }
        return ventes.stream()
                .map(venteMapper::toVenteDto)
                .collect(Collectors.toList());
    }

    public List<VenteDto> getVentesByDateRange(Date startDate, Date endDate) {
        Integer userId = userService.getCurrentUserId();
        List<Vente> ventes = venteRepository.findByCreatedAtBetweenAndCreatedBy(startDate, endDate, userId);
        if (ventes.isEmpty()) {
            throw new AppException("Aucune vente trouvée pour cette plage de dates", HttpStatus.NOT_FOUND);
        }
        return ventes.stream()
                .map(venteMapper::toVenteDto)
                .collect(Collectors.toList());
    }

    public int getTotalVentesByDateRange(Date startDate, Date endDate) {
        Integer userId = userService.getCurrentUserId();
        int totalVentes = venteRepository.sumPrixVenteByCreatedAtBetweenAndCreatedBy(startDate, endDate, userId);
        if (totalVentes == null) {
            throw new AppException("Aucune vente trouvée pour cette plage de dates", HttpStatus.NOT_FOUND);
        }
        return totalVentes;
    }

    public boolean checkProduitDisponibilite(int produitId, int quantiteDemandee) {
        int stockDisponible = approvisionnementService.getStockDisponible(produitId);
        if (stockDisponible < quantiteDemandee) {
            throw new AppException("Quantité demandée non disponible", HttpStatus.BAD_REQUEST);
        }
        return true;
    }

    public double getTotalRevenue() {
        List<Vente> ventes = venteRepository.findAll();
        return ventes.stream().mapToDouble(v -> v.getQuantite() * v.getPrixVente()).sum();
    }

    public double getTotalRevenueByProduit(int produitId) {
        List<Vente> ventes = venteRepository.findByProduitIdProduit(produitId);
        return ventes.stream().mapToDouble(v -> v.getQuantite() * v.getPrixVente()).sum();
    }

    public VenteDto cancelVente(Long id) {
        // Implémentez selon vos besoins
        return new VenteDto();
    }

    public List<VenteDto> getVentesByStatus(String status) {
        EnumVente enumStatus;
        try {
            enumStatus = EnumVente.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException("Invalid status value: " + status, HttpStatus.BAD_REQUEST);
        }

        List<Vente> ventes = venteRepository.findByStatus(enumStatus);
        if (ventes.isEmpty()) {
            throw new AppException("No sales found with the given status", HttpStatus.NOT_FOUND);
        }
        return ventes.stream()
                .map(venteMapper::toVenteDto)
                .collect(Collectors.toList());
    }

    public List<VenteDto> getVentesByPaymentMode(String paymentMode) {
        EnumPayementMode enumPayementMode;
        try {
            enumPayementMode = EnumPayementMode.valueOf(paymentMode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException("Invalid paymentMode value: " + paymentMode, HttpStatus.BAD_REQUEST);
        }
        List<Vente> ventes = venteRepository.findByPayementMode(enumPayementMode);
        if (ventes.isEmpty()) {
            throw new AppException("No sales found with the given payment mode", HttpStatus.NOT_FOUND);
        }
        return ventes.stream()
                .map(venteMapper::toVenteDto)
                .collect(Collectors.toList());
    }

    public VenteDto updateVenteStatus(Long id, String status) {
        Vente vente = venteRepository.findByIdAndCreatedBy(id, userService.getCurrentUserId())
                .orElseThrow(() -> new AppException("Vente not found", HttpStatus.NOT_FOUND));
        try {
            EnumVente venteStatus = EnumVente.valueOf(status.toUpperCase());
            vente.setStatus(venteStatus);
        } catch (IllegalArgumentException e) {
            throw new AppException("Invalid status value", HttpStatus.BAD_REQUEST);
        }
        return venteMapper.toVenteDto(venteRepository.save(vente));
    }


    public void deleteOldVentes(Date olderThanDate) {
        List<Vente> oldVentes = venteRepository.findByCreatedAtBefore(olderThanDate);
        venteRepository.deleteAll(oldVentes);
    }

    public VenteDto restoreCancelledVente(Long id) {
        // Implémentez selon vos besoins
        return null;
    }*/
}