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

    @Override
    public String getEmail() {
        return "";
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getPhone() {
        return "";
    }

    @Override
    public <E extends Enum<E>> Enum<E> getRole() {
        return null;
    }
}
