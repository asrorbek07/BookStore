package uz.kruz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kruz.domain.vo.BaseEntity;

import java.util.List;

@Data
@NoArgsConstructor
public class Author extends BaseEntity {
    private String fullName;

    private List<Book> books;

    public Author(Integer id, String fullName, java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt, List<Book> books) {
        super(id, createdAt, updatedAt);
        this.fullName = fullName;
        this.books = books;
    }
}
