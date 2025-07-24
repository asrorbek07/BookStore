package uz.kruz.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
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

    @Override
    public String toString() {
        return "Book{" +
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
