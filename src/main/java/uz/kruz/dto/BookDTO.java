package uz.kruz.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for the Book entity.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BookDTO extends BaseDTO {
    private String title;
    private String isbn;
    private BigDecimal price;
    private Integer stock;
    private Integer publishedYear;
    private Integer categoryId;
    private Integer publisherId;
    private List<Integer> authorIds; // Only store IDs of related authors, not the full objects
    private List<Integer> reviewIds; // Only store IDs of related reviews, not the full objects

    public BookDTO(Integer id, String title, String isbn, BigDecimal price, Integer stock, 
                  Integer publishedYear, Integer categoryId, Integer publisherId, 
                  LocalDateTime createdAt, LocalDateTime updatedAt,
                  List<Integer> authorIds, List<Integer> reviewIds) {
        super(id, createdAt, updatedAt);
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.stock = stock;
        this.publishedYear = publishedYear;
        this.categoryId = categoryId;
        this.publisherId = publisherId;
        this.authorIds = authorIds;
        this.reviewIds = reviewIds;
    }
}