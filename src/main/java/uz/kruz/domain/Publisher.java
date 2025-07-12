package uz.kruz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kruz.domain.vo.BaseEntity;

/**
 * Domain class representing the publishers table in the database.
 */
@Data
@NoArgsConstructor
public class Publisher extends BaseEntity {
    private String name;
    private String contactEmail;
    private String phone;

    public Publisher(Integer id, String name, String contactEmail, String phone, java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.contactEmail = contactEmail;
        this.phone = phone;
    }
}
