package uz.kruz.service;

import uz.kruz.dto.BaseDTO;

import java.util.List;

public interface AuthorService<D extends BaseDTO> extends Service<D, Integer> {

    List<D> findByName(String name);

    List<D> findByBookId(Integer bookId);
}
