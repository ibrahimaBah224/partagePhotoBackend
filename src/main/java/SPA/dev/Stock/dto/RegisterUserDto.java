package SPA.dev.Stock.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    private String fullName;

    private String email;

    private String telephone;

    private String password;

    private int createdBy;

    private int idMagasin;
    // getters and setters here...
}
