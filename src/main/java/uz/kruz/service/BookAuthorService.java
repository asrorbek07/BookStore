package uz.kruz.service;

import uz.kruz.dto.BaseDTO;

import java.util.List;
import java.util.Optional;

public interface BookAuthorService<D extends BaseDTO> extends Service<D, Integer> {

    List<D> findByBookId(Integer bookId);

    List<D> findByAuthorId(Integer authorId);

    Optional<D> findByBookIdAndAuthorId(Integer bookId, Integer authorId);
}
