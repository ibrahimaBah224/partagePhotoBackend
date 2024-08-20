package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.VenteDto;
import SPA.dev.Stock.enumeration.EnumPayementMode;
import SPA.dev.Stock.enumeration.EnumVente;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.VenteMapper;
import SPA.dev.Stock.modele.Produit;
import SPA.dev.Stock.modele.Vente;
import SPA.dev.Stock.modele.VenteInit;
import SPA.dev.Stock.repository.ProduitRepository;
import SPA.dev.Stock.repository.VenteInitRepository;
import SPA.dev.Stock.repository.VenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VenteService {

    private final VenteRepository venteRepository;
    private final VenteMapper venteMapper;
    private final UserService userService;
    private final ApprovisionnementService approvisionnementService;
    private final ProduitRepository produitRepository;
    private final VenteInitRepository venteInitRepository;

    public List<VenteDto> getAll() {
        Integer userId = userService.getCurrentUserId();
        List<Vente> ventes = venteRepository.findByCreatedBy(userId);
        return venteMapper.toVenteDtoList(ventes);
    }


    public VenteDto getVente(Long id) {
        Vente vente = venteRepository.findByIdAndCreatedBy(id, userService.getCurrentUserId())
                .orElseThrow(() -> new AppException("Vente not found", HttpStatus.NOT_FOUND));
        return venteMapper.toVenteDto(vente);
    }


    public List<VenteDto> getVenteByProduit(int id) {
        List<Vente> ventes = venteRepository.findByProduitIdProduitAndCreatedBy(id, userService.getCurrentUserId());
        if (ventes.isEmpty()) {
            throw new AppException("Pas de vente liée à ce produit", HttpStatus.NOT_FOUND);
        }
        return venteMapper.toVenteDtoList(ventes);
    }


    public VenteDto addVente(VenteDto venteDto) {
        Produit produit = produitRepository.findById(Math.toIntExact(venteDto.getProduitId()))
                .orElseThrow(() -> new AppException("Produit not found", HttpStatus.NOT_FOUND));
        VenteInit venteInit = venteInitRepository.findById(venteDto.getVenteInitId())
                .orElseThrow(() -> new AppException("VenteInit not found", HttpStatus.NOT_FOUND));

        Vente vente = Vente.builder()
                .produit(produit)
                .venteInit(venteInit)
                .quantite(venteDto.getQuantite())
                .prixVente(venteDto.getPrixVente())
                .discount(venteDto.getDiscount())
                .payementMode(venteDto.getPayementMode())
                .build();

        vente.setCreatedBy(userService.getCurrentUserId());
        Vente newVente = venteRepository.save(vente);
        return venteMapper.toVenteDto(newVente);
    }


    /**
     * Supprime une vente spécifique et restaure le stock.
     *
     * @param id l'ID de la vente à supprimer.
     * @return l'objet VenteDto supprimé.
     * @throws AppException si la vente n'existe pas.
     */
    public VenteDto removeVente(Long id) {
        Vente vente = venteRepository.findByIdAndCreatedBy(id, userService.getCurrentUserId())
                .orElseThrow(() -> new AppException("Vente not found", HttpStatus.NOT_FOUND));
        venteRepository.deleteById(id);
        return venteMapper.toVenteDto(vente);
    }

    /**
     * Met à jour une vente spécifique pour l'utilisateur actuel.
     *
     * @param id l'ID de la vente à mettre à jour.
     * @param venteDto les nouvelles données de la vente.
     * @return l'objet VenteDto mis à jour.
     * @throws AppException si la vente n'existe pas.
     */
    public VenteDto updateVente(Long id, VenteDto venteDto) {
        Vente vente = venteRepository.findByIdAndCreatedBy(id, userService.getCurrentUserId())
                .orElseThrow(() -> new AppException("Vente not found", HttpStatus.NOT_FOUND));

        Produit produit = produitRepository.findById(Math.toIntExact(venteDto.getProduitId()))
                .orElseThrow(() -> new AppException("Produit not found", HttpStatus.NOT_FOUND));
        VenteInit venteInit = venteInitRepository.findById(venteDto.getVenteInitId())
                .orElseThrow(() -> new AppException("VenteInit not found", HttpStatus.NOT_FOUND));

        vente.setProduit(produit);
        vente.setVenteInit(venteInit);
        vente.setQuantite(venteDto.getQuantite());
        vente.setPrixVente(venteDto.getPrixVente());
        vente.setDiscount(venteDto.getDiscount());
        vente.setPayementMode(venteDto.getPayementMode());

        return venteMapper.toVenteDto(venteRepository.save(vente));
    }


    public List<VenteDto> getVentesByDate(Date date) {
        Integer userId = userService.getCurrentUserId();
        List<Vente> ventes = venteRepository.findByCreatedAtAndCreatedBy(date, userId);
        if (ventes.isEmpty()) {
            throw new AppException("Aucune vente trouvée pour cette date", HttpStatus.NOT_FOUND);
        }
        return venteMapper.toVenteDtoList(ventes);
    }

    public List<VenteDto> getVentesByDateRange(Date startDate, Date endDate) {
        Integer userId = userService.getCurrentUserId();
        List<Vente> ventes = venteRepository.findByCreatedAtBetweenAndCreatedBy(startDate, endDate, userId);
        if (ventes.isEmpty()) {
            throw new AppException("Aucune vente trouvée pour cette plage de dates", HttpStatus.NOT_FOUND);
        }
        return venteMapper.toVenteDtoList(ventes);
    }

    public double getTotalVentesByDateRange(Date startDate, Date endDate) {
        Integer userId = userService.getCurrentUserId();
        Double totalVentes = venteRepository.sumPrixVenteByDateVenteBetweenAndCreatedBy(startDate, endDate, userId);
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

    /*
    public List<VenteDto> getVentesByClient(int clientId) {
        List<Vente> ventes = venteRepository.findByClientId(clientId);
        if (ventes.isEmpty()) {
            throw new AppException("No sales found for this client", HttpStatus.NOT_FOUND);
        }
        return venteMapper.toVenteDtoList(ventes);
    }
     */

    public double getTotalRevenue() {
        List<Vente> ventes = venteRepository.findAll();
        return ventes.stream().mapToDouble(v -> v.getQuantite() * v.getPrixVente()).sum();
    }

    public double getTotalRevenueByProduit(int produitId) {
        List<Vente> ventes = venteRepository.findByProduitIdProduit(produitId);
        return ventes.stream().mapToDouble(v -> v.getQuantite() * v.getPrixVente()).sum();
    }


    public VenteDto cancelVente(Long id) {
        return new VenteDto();
    }


    public List<VenteDto> getVentesByStatus(String status) {
        EnumVente enumStatus;
        try {
            enumStatus = EnumVente.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(STR."Invalid status value: \{status}", HttpStatus.BAD_REQUEST);
        }

        List<Vente> ventes = venteRepository.findByStatus(enumStatus);
        if (ventes.isEmpty()) {
            throw new AppException("No sales found with the given status", HttpStatus.NOT_FOUND);
        }
        return venteMapper.toVenteDtoList(ventes);
    }


    public List<VenteDto> getVentesByPaymentMode(String paymentMode) {
        EnumPayementMode enumPayementMode;
        try {
            enumPayementMode = EnumPayementMode.valueOf(paymentMode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(STR."Invalid payementMode value: \{paymentMode}", HttpStatus.BAD_REQUEST);
        }
        List<Vente> ventes = venteRepository.findByPayementMode(enumPayementMode);
        if (ventes.isEmpty()) {
            throw new AppException("No sales found with the given payment mode", HttpStatus.NOT_FOUND);
        }
        return venteMapper.toVenteDtoList(ventes);
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

    public double calculateDiscountedPrice(VenteDto venteDto) {
        double originalPrice = venteDto.getQuantite() * venteDto.getPrixVente();
        Double discount = venteDto.getDiscount(); // Retourne un Double, pouvant être nul
        double effectiveDiscount = (discount != null) ? discount : 0.0; // Assure que effectiveDiscount est un double
        return originalPrice - (originalPrice * effectiveDiscount / 100.0);
    }


    public void deleteOldVentes(Date olderThanDate) {
        List<Vente> oldVentes = venteRepository.findByCreatedAtBefore(olderThanDate);
        venteRepository.deleteAll(oldVentes);
    }

    public VenteDto restoreCancelledVente(Long id) {
        return null;
    }
}
