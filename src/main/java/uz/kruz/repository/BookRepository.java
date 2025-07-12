package uz.kruz.repository;

import uz.kruz.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends Repository<Book, Integer> {

    Optional<Book> retrieveByIsbn(String isbn);

    List<Book> retrieveByTitle(String title);

    List<Book> retrieveByCategoryId(Integer categoryId);

    List<Book> retrieveByPublisherId(Integer publisherId);

    List<Book> retrieveByAuthorId(Integer authorId);

    List<Book> retrieveByStockLessThan(Integer amount);
}
