package uz.kruz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
public class BaseDTO {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}