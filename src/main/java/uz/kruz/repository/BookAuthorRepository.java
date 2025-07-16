package uz.kruz.repository;

import uz.kruz.domain.BookAuthor;

import java.util.List;
import java.util.Optional;

public interface BookAuthorRepository extends Repository<BookAuthor, Integer> {

    List<BookAuthor> retrieveAll(String name);

    List<BookAuthor> retrieveByBookId(Integer bookId);

    List<BookAuthor> retrieveByAuthorId(Integer authorId);

    Optional<BookAuthor> retrieveByBookIdAndAuthorId(Integer bookId, Integer authorId);
}
