package SPA.dev.Stock.dto;

import lombok.*;

import java.util.Date;

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
    private Date createdAt;
    private Date updatedAt;
    private int createdBy;
    private int updatedBy;
}
