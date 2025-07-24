package uz.kruz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for the Book entity.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
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

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", publishedYear=" + publishedYear +
                ", categoryId=" + categoryId +
                ", publisherId=" + publisherId +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }

}