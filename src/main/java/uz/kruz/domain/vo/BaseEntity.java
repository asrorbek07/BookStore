package uz.kruz.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Abstract base class for all domain entities.
 * Implements the Entity interface and provides common fields.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity implements Entity {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}