package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.AchatPanierDto;
import SPA.dev.Stock.dto.AchatProduitDto;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchatPanierService {
    private final AchatPanierRepository achatPanierRepository;
    private final Mapper achatMapper;
    private final UserService userService;
    private  final ProduitRepository produitRepository;
    private final UserRepository userRepository;
    private final ApprovisionnementRepository approvisionnementRepository;
    private final PerteRepository perteRepository;
    private final TransfertService transfertService;
    private final AchatInitRepository achatInitRepository;

    public List<AchatPanierDto> getAll() {
        Integer userId = userService.getCurrentUserId();
        List<AchatPanier> achats = achatPanierRepository.findByCreatedBy(userId);
        return achats.stream()
                .map(achatMapper::toAchatPanierDto)
                .collect(Collectors.toList());
    }

    public AchatPanierDto getAchat(Long id) {
        AchatPanier vente = achatPanierRepository.findByIdAndCreatedBy(id, userService.getCurrentUserId())
                .orElseThrow(() -> new AppException("achat panier not found", HttpStatus.NOT_FOUND));
        return achatMapper.toAchatPanierDto(vente);
    }


    public AchatPanierDto addAchat(AchatPanierDto achatPanierDto) {
        User user = userRepository.findById(userService.getCurrentUserId())
                .orElseThrow(()->new RuntimeException("user not found"));
        if(achatPanierDto.getIdAchatInit() == null){
            throw new RuntimeException("Achat Init ne peut pas être null");
        }
        Produit produit = produitRepository.findById(achatPanierDto.getIdProduit())
                .orElseThrow(() -> new AppException("Produit not found", HttpStatus.NOT_FOUND));
        AchatInit achatInit = achatInitRepository.findById(achatPanierDto.getIdAchatInit())
                .orElseThrow(() -> new AppException("VenteInit not found", HttpStatus.NOT_FOUND));
        AchatPanier achatPanier = achatPanierRepository.findByProduitAndCreatedByAndAchatInit(produit,userService.getCurrentUserId(),achatInit);


        if(achatPanierDto.getQuantite() <= 0){
            throw new RuntimeException("quantité non autorisé");
        }
        if(achatPanier!=null){
            throw new RuntimeException("produit existant");
        }else {
            if (achatPanierDto.getQuantite() > transfertService.stockProduit(produit)){
                throw new RuntimeException("la quantité en parametre est superieur au stock restant❌");
            }else {
                AchatPanier achatPanier1 = achatMapper.toAchatPanierEntity(achatPanierDto, produit, achatInit);
                achatPanier1.setCreatedBy(userService.getCurrentUserId());

                AchatPanier newAchat = achatPanierRepository.save(achatPanier1);
                return achatMapper.toAchatPanierDto(newAchat);
            }
        }
    }

    public Object[] achatEnCours(int idAchatVente){
        List<AchatPanier> achatEnCours = achatPanierRepository.findByAchatInitIdAndStatus(idAchatVente,1);
        List<AchatProduitDto> produitAchatDtos = new ArrayList<>();
        for (AchatPanier achatPanier :achatEnCours){
            AchatProduitDto achatProduitDto = new AchatProduitDto();

            achatProduitDto.setDesignation(achatPanier.getProduit().getDesignation());
            achatProduitDto.setIdVente(achatPanier.getAchatInit().getId());
            achatProduitDto.setQuantite(achatPanier.getQuantite());
            achatProduitDto.setPrixUnitaire(achatPanier.getPrixUnitaire());
            achatProduitDto.setId(achatPanier.getId());

            produitAchatDtos.add(achatProduitDto);
        }
        return new Object[]{produitAchatDtos,getTotalRevenue(produitAchatDtos)};
    }
    public double getTotalRevenue(List<AchatProduitDto> produitVenteDtos) {
        return produitVenteDtos.stream().mapToDouble(v -> v.getQuantite() * v.getPrixUnitaire()).sum();
    }


    public AchatPanierDto removeAchat(Long id) {
        AchatPanier achatPanier = achatPanierRepository.findByIdAndCreatedBy(id, userService.getCurrentUserId())
                .orElseThrow(() -> new AppException("achat panier not found", HttpStatus.NOT_FOUND));
        achatPanierRepository.deleteById(id);
        return achatMapper.toAchatPanierDto(achatPanier);
    }

   /* public VenteDto updateVente(Long id, VenteDto venteDto) {
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
    }*/
}
