package SPA.dev.Stock.modele;

import SPA.dev.Stock.service.UserService;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntitie implements Serializable {

    @Transient
    private UserService userService;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "updated_by")
    private int updatedBy;

    @PrePersist
    protected void onCreate() {
        createdBy = userService.getCurrentUserId();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedBy = userService.getCurrentUserId();
    }

    @Column(columnDefinition = "integer default 1", nullable = false)
    private int status = 1;

}
