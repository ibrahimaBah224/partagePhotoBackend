package SPA.dev.Stock.modele;

import SPA.dev.Stock.service.UserService;
import jakarta.persistence.*;
import jdk.jfr.Registered;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntitie implements Serializable {


    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;


    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @CreatedBy
    @Column(name = "created_by",updatable = false)
    private int createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private int updatedBy;

    @Column(columnDefinition = "integer default 1", nullable = false)
    private int statut = 1;

}
