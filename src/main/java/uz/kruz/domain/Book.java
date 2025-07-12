package uz.kruz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kruz.domain.vo.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class Book extends BaseEntity {
    private String title;
    private String isbn;
    private BigDecimal price;
    private Integer stock;
    private Integer publishedYear;
    private Integer categoryId;
    private Integer publisherId;

    private Category category;
    private Publisher publisher;
    private List<Author> authors;
    private List<Review> reviews;

    public Book(Integer id, String title, String isbn, BigDecimal price, Integer stock, 
                Integer publishedYear, Integer categoryId, Integer publisherId, 
                java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt,
                Category category, Publisher publisher, List<Author> authors, List<Review> reviews) {
        super(id, createdAt, updatedAt);
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.stock = stock;
        this.publishedYear = publishedYear;
        this.categoryId = categoryId;
        this.publisherId = publisherId;
        this.category = category;
        this.publisher = publisher;
        this.authors = authors;
        this.reviews = reviews;
    }
}
