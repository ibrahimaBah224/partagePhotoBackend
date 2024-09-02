package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.ProduitVenteDto;
import SPA.dev.Stock.dto.VenteDto;
import SPA.dev.Stock.exception.AppException;
import SPA.dev.Stock.mapper.Mapper;
import SPA.dev.Stock.modele.*;
import SPA.dev.Stock.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final UserRepository userRepository;

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
        User user = userRepository.findById(userService.getCurrentUserId())
                .orElseThrow(()->new RuntimeException("user not found"));
        user = userRepository.findById(user.getCreatedBy()).orElseThrow(()-> new RuntimeException("super not found"));
        if(venteDto.getVenteInitId() == null){
            throw new RuntimeException("vente Init ne peut pas être null");
        }
        Produit produit = produitRepository.findById(venteDto.getIdProduit())
                .orElseThrow(() -> new AppException("Produit not found", HttpStatus.NOT_FOUND));
        VenteInit venteInit = venteInitRepository.findById(venteDto.getVenteInitId())
                .orElseThrow(() -> new AppException("VenteInit not found", HttpStatus.NOT_FOUND));
        Vente venteProduit = venteRepository.findByProduit(produit);
        if(venteProduit!=null){
            throw new RuntimeException("produit existant");
        }else {
            Vente vente = venteMapper.toVenteEntity(venteDto, produit, venteInit);
            vente.setCreatedBy(userService.getCurrentUserId());
            vente.setUser(user);
            Vente newVente = venteRepository.save(vente);
            return venteMapper.toVenteDto(newVente);
        }
    }

    public Object[] venteEnCours(int idVenteInit){
        List<Vente> ventesEnCours = venteRepository.findByVenteInitIdAndStatus(idVenteInit,1);
        List<ProduitVenteDto> produitVenteDtos = new ArrayList<>();
        for (Vente vente :ventesEnCours){
            ProduitVenteDto produitVenteDto = new ProduitVenteDto();

            produitVenteDto.setDesignation(vente.getProduit().getDesignation());
            produitVenteDto.setIdVente(vente.getVenteInit().getId());
            produitVenteDto.setQuantite(vente.getQuantite());
            produitVenteDto.setPrixUnitaire(vente.getPrixVente());
            produitVenteDto.setId(vente.getId());

            produitVenteDtos.add(produitVenteDto);
        }
        return new Object[]{produitVenteDtos,getTotalRevenue(produitVenteDtos)};
    }
    public double getTotalRevenue(List<ProduitVenteDto> produitVenteDtos) {
        return produitVenteDtos.stream().mapToDouble(v -> v.getQuantite() * v.getPrixUnitaire()).sum();
    }

/*
    public Object[] venteEnCours(int idVenteInit) {
        List<Object[]> result = venteRepository.findQuantiteTotaleParProduit(idVenteInit, 1);
        List<ProduitVenteDto> produitVenteDtos = new ArrayList<>();
        for (Object[] row : result) {
            ProduitVenteDto produitVenteDto = new ProduitVenteDto();

            produitVenteDto.setIdVente((Integer) row[0]);
            produitVenteDto.setDesignation((String) row[1]);
            produitVenteDto.setPrixUnitaire((Double) row[2]);
            produitVenteDto.setQuantite(((Number) row[3]).intValue());

            produitVenteDtos.add(produitVenteDto);
        }
        double totalRevenue = getTotalRevenue(produitVenteDtos);
        return new Object[]{produitVenteDtos, totalRevenue};
    }

    // Méthode pour calculer le revenu total
    private double getTotalRevenue(List<ProduitVenteDto> produitVenteDtos) {
        return produitVenteDtos.stream()
                .mapToDouble(dto -> dto.getQuantite() * dto.getPrixUnitaire())
                .sum();
    }

*/

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