package SPA.dev.Stock.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Data
public class AbstractEntitieDto  {
    private int createdBy;
    private Date createdAt;
    private int updatedBy;
    private Date updatedAt;
}
