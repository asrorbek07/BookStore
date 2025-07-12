package uz.kruz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kruz.domain.vo.BaseEntity;

import java.time.LocalDateTime;

/**
 * Domain class representing the reviews table in the database.
 */
@Data
@NoArgsConstructor
public class Review extends BaseEntity {
    private Integer userId;
    private Integer bookId;
    private Integer rating;  // Between 1 and 5
    private String comment;
    private LocalDateTime reviewedAt; // Maps to createdAt in BaseEntity

    // Relationships (not stored in database directly)
    private User user;
    private Book book;

    public Review(Integer id, Integer userId, Integer bookId, Integer rating, String comment,
                 LocalDateTime reviewedAt, LocalDateTime updatedAt, User user, Book book) {
        super(id, null, updatedAt); // Pass null for createdAt, will be set by setCreatedAt override
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
        this.reviewedAt = reviewedAt; // This will also set createdAt via the override
        this.user = user;
        this.book = book;
    }

    // Map reviewedAt to createdAt for BaseEntity
    @Override
    public LocalDateTime getCreatedAt() {
        return reviewedAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.reviewedAt = createdAt;
    }
}
