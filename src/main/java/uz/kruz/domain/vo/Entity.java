package uz.kruz.domain.vo;

import java.time.LocalDateTime;

/**
 * Common interface for all domain entities.
 * Defines the common fields that all entities should have.
 */
public interface Entity {
    Integer getId();
    void setId(Integer id);
    
    LocalDateTime getCreatedAt();
    void setCreatedAt(LocalDateTime createdAt);
    
    LocalDateTime getUpdatedAt();
    void setUpdatedAt(LocalDateTime updatedAt);
}