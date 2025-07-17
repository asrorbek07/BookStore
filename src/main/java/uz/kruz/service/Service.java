package uz.kruz.service;

import uz.kruz.dto.BaseDTO;

import java.util.List;
import java.util.Optional;

public interface Service<D extends BaseDTO, T, ID> {

    T register(D dto);

    Optional<T> findById(ID id);

    List<T> findAll();

    boolean removeById(ID id);

    T modify(D dto, ID id);

    long count();
}
