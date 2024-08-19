package SPA.dev.Stock.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class ProduitDto {
    private Long id;
    @NotNull
    private String designation;
    private String description;
}
