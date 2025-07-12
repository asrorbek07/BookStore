package uz.kruz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kruz.domain.vo.BaseEntity;

/**
 * Domain class representing the categories table in the database.
 */
@Data
@NoArgsConstructor
public class Category extends BaseEntity {
    private String name;

    public Category(Integer id, String name, java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.name = name;
    }
}
