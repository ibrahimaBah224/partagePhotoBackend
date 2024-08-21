package SPA.dev.Stock.dto;

import SPA.dev.Stock.enumeration.EnumOperation;
import SPA.dev.Stock.enumeration.EnumPayementMode;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaisseDto {
    private Long id;
    private EnumPayementMode typePaiement;
    private EnumOperation typeOperation;
    private int montant;
    private String motif;
}
