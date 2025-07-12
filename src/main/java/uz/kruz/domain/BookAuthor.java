package uz.kruz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kruz.domain.vo.BaseEntity;

/**
 * Domain class representing the book_authors junction table in the database.
 * This is a many-to-many relationship between books and authors.
 */
@Data
@NoArgsConstructor
public class BookAuthor extends BaseEntity {
    private Integer bookId;
    private Integer authorId;

    // Relationships (not stored in database directly)
    private Book book;
    private Author author;

    public BookAuthor(Integer bookId, Integer authorId, java.time.LocalDateTime createdAt, 
                     java.time.LocalDateTime updatedAt, Book book, Author author) {
        super(null, createdAt, updatedAt);
        this.bookId = bookId;
        this.authorId = authorId;
        this.book = book;
        this.author = author;
    }

    // Override BaseEntity methods for composite key
    @Override
    public Integer getId() {
        return null; // This entity uses a composite key (bookId, authorId)
    }

    @Override
    public void setId(Integer id) {
        // No-op as this entity uses a composite key
    }
}
