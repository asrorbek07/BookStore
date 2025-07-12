package uz.kruz.service;

import uz.kruz.dto.BaseDTO;

import java.util.List;
import java.util.Optional;

public interface BookService<D extends BaseDTO> extends Service<D, Integer> {

    Optional<D> findByIsbn(String isbn);

    List<D> findByTitle(String title);

    List<D> findByCategoryId(Integer categoryId);

    List<D> findByPublisherId(Integer publisherId);

    List<D> findByAuthorId(Integer authorId);

    List<D> findByStockLessThan(Integer amount);
}
