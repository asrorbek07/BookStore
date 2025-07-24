package uz.kruz.service;

import uz.kruz.domain.Book;
import uz.kruz.dto.BaseDTO;
import uz.kruz.dto.BookDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookService extends Service<BookDTO, Book, Integer> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByTitle(String title);

    List<Book> findByCategoryId(Integer categoryId);

    List<Book> findByPublisherId(Integer publisherId);

    List<Book> findByAuthorId(Integer authorId);

    List<Book> findByStockLessThan(Integer amount);
    BigDecimal getPriceById(Integer id);
}
