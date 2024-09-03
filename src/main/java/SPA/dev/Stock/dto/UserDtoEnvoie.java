package SPA.dev.Stock.dto;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoEnvoie {
    @Null
    private int id;

    private String fullName;

    private String email;

    private String telephone;

    private String password;

    private String Role;

    private int createdBy;

    private String nomMagasin;
    private int idMagasin;
    // getters and setters here...
}
