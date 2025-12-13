package by.bntu.salaryapp.domain.model;

import by.bntu.salaryapp.domain.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class BaseAuditingEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4681401402666658611L;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", updatable = false)
    @JsonIgnore//ignore completely to avoid StackOverflow exception by User.createdByUser logic, use DTO
    private User createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    @JsonIgnore//ignore completely to avoid StackOverflow exception by User.lastModifiedByUser logic, use DTO
    private User updatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
