package SPA.dev.Stock.dto;

import SPA.dev.Stock.enumeration.RoleEnumeration;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private String token;

    private long expiresIn;

    // Getters and setters...
}
