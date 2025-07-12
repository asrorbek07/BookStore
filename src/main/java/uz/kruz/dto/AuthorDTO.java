package uz.kruz.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for the Author entity.
 */
@Data
@NoArgsConstructor
public class AuthorDTO extends BaseDTO {
    private String fullName;
    private List<Integer> bookIds; // Only store IDs of related books, not the full objects

    public AuthorDTO(Integer id, String fullName, LocalDateTime createdAt, LocalDateTime updatedAt, List<Integer> bookIds) {
        super(id, createdAt, updatedAt);
        this.fullName = fullName;
        this.bookIds = bookIds;
    }
}