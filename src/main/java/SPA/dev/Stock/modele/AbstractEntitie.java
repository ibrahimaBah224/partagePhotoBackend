package SPA.dev.Stock.modele;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntitie implements Serializable {


    @CreatedBy
    @Column(nullable = true)
    private String createdBy ;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date createdAt;

    @LastModifiedBy
    @Column(nullable = true)
    private String updatedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date updatedAt;

    @Column(columnDefinition = "integer default 1", nullable = false)
    private int status = 1;

}
