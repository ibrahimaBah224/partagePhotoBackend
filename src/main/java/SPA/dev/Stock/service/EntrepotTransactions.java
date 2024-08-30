package SPA.dev.Stock.service;

import SPA.dev.Stock.dto.EntrepotTransactionsDto;
import SPA.dev.Stock.enumeration.EnumTypeMagasin;
import SPA.dev.Stock.mapper.EntrepotTransactionsMapper;
import SPA.dev.Stock.modele.Magasin;
import SPA.dev.Stock.modele.User;
import SPA.dev.Stock.repository.EntrepotTransactionsRepository;
import SPA.dev.Stock.repository.MagasinRepository;
import SPA.dev.Stock.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EntrepotTransactions {
    private final EntrepotTransactionsRepository entrepotTransactionsRepository;
    private final EntrepotTransactionsMapper entrepotTransactionsMapper;
    private final MagasinRepository magasinRepository;
    private final UserService userService;
    private final UserRepository userRepository;


    public EntrepotTransactionsDto  add(EntrepotTransactionsDto entrepotTransactionsDto){
        int currentUserId = userService.getCurrentUserId();
         User user = userRepository.findById(currentUserId).orElseThrow(()->new RuntimeException("user not found"));
        Magasin magasin = magasinRepository.findById(entrepotTransactionsDto.getIdEntrepot())
                .orElseThrow(()->new RuntimeException("Entrepot non trouvé"));
        if(magasin.getUser()!=user){
            if(magasin.getCreatedBy()!=currentUserId){
                throw new RuntimeException("Vous ne pouvez pas effectuer cette action sur cet entrepot");
            }
        }
        if(!magasin.getTypeMagasin().equals(EnumTypeMagasin.ENTREPOT)){
            throw new RuntimeException("Veuillez selectionner un entrepot");
        }
        return entrepotTransactionsMapper.toDto(
                entrepotTransactionsRepository.save(
                entrepotTransactionsMapper.toEntity(entrepotTransactionsDto)
        ));
    }
    /*public EntrepotTransactionsDto update(int id,EntrepotTransactionsDto entrepotTransactionsDto){
        int currentUserId = userService.getCurrentUserId();
        User user = userRepository.findById(currentUserId).orElseThrow(()->new RuntimeException("user not found"));


    }*/
}
