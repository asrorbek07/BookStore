package uz.kruz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kruz.domain.vo.BaseEntity;
import uz.kruz.domain.vo.UserRole;

import java.util.List;

/**
 * Domain class representing the users table in the database.
 */
@Data
@NoArgsConstructor
public class User extends BaseEntity {
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private UserRole role;

    // Relationships (not stored in database directly)
    private List<Order> orders;
    private List<Review> reviews;

    public User(Integer id, String fullName, String email, String password, String phone, 
                UserRole role, java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt,
                List<Order> orders, List<Review> reviews) {
        super(id, createdAt, updatedAt);
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.orders = orders;
        this.reviews = reviews;
    }
}
