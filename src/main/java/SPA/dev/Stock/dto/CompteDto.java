package SPA.dev.Stock.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class CompteDto {
    private Long id;
    private String reference;
    private String apiKey;
    private String description;
    private String numero;

}
