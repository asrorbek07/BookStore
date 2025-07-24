package uz.kruz.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class BookAuthor extends BaseEntity {
    private Integer bookId;
    private Integer authorId;

    private Book book;
    private Author author;

    @Override
    public String toString() {
        return "BookAuthor{" +
                "id=" + getId() +
                ", bookId=" + bookId +
                ", authorId=" + authorId +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
