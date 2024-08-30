package SPA.dev.Stock.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EntrepotTransactionsDto {
    private  int id;
    private int idProduit;
    private int idEntrepot;
    private String operation;
    private int quantite;
    private Date dateTransaction;
}
