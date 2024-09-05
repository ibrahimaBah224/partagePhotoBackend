package SPA.dev.Stock.dto;


import SPA.dev.Stock.enumeration.EnumPayementMode;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class AchatInitDto {
    private Long id;
    private String reference;

    private Date createdAt;
    private Date updatedAt;
    private int createdBy;
    private int updatedBy;
    private int status;
}
