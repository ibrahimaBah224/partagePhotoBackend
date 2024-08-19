package SPA.dev.Stock.dto;

import SPA.dev.Stock.enumeration.RoleEnumeration;
import lombok.Data;

@Data
public class RegisterUserDto {

    private String fullName;

    private String email;

    private String telephone;

    private String password;

    private RoleEnumeration role;

    private int createdBy;
    // getters and setters here...
}
