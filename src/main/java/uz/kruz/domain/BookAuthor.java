package uz.kruz.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class BookAuthor extends BaseEntity {
    private Integer bookId;
    private Integer authorId;

    // Relationships (not stored in database directly)
    private Book book;
    private Author author;

}
