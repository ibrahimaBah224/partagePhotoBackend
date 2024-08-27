package SPA.dev.Stock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Null;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    @Null
    private int id;

    private String fullName;

    private String email;

    private String telephone;

    private String password;

    private int createdBy;

    private int idMagasin;
    // getters and setters here...
}
