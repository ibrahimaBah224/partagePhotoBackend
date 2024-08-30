package SPA.dev.Stock.mapper;

import SPA.dev.Stock.dto.EntrepotTransactionsDto;
import SPA.dev.Stock.modele.EntrepotTransactions;
import SPA.dev.Stock.repository.MagasinRepository;
import SPA.dev.Stock.repository.ProduitRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class EntrepotTransactionsMapper {
    private final MagasinRepository entrepotRepository;
    private final ProduitRepository produitRepository;
    public EntrepotTransactionsDto toDto(EntrepotTransactions entrepotTransactions){
        return EntrepotTransactionsDto.builder()
                .id(entrepotTransactions.getId())
                .dateTransaction(entrepotTransactions.getCreatedAt())
                .idEntrepot(entrepotTransactions.getEntrepot().getId())
                .idProduit(entrepotTransactions.getProduit().getIdProduit())
                .quantite(entrepotTransactions.getQuantite())
                .build();
    }
    public EntrepotTransactions toEntity(EntrepotTransactionsDto entrepotTransactionsDto){
        return EntrepotTransactions.builder()
               .id(entrepotTransactionsDto.getId())
               .createdAt(entrepotTransactionsDto.getDateTransaction())
               .entrepot(entrepotRepository.findById(entrepotTransactionsDto.getIdEntrepot())
                       .orElseThrow(()->new RuntimeException("entrepot introuvable")))
               .produit(produitRepository.findById(entrepotTransactionsDto.getIdProduit())
                       .orElseThrow(()->new RuntimeException("produit introuvable")))
               .quantite(entrepotTransactionsDto.getQuantite())
               .build();
    }
    public List<EntrepotTransactionsDto> toDtoList(List<EntrepotTransactions> entrepotTransactions){
        return entrepotTransactions.stream().map(this::toDto).collect(toList());
    }
}
