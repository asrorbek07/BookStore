package uz.kruz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Base class for all DTOs.
 * Contains common fields that are present in all entities.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}