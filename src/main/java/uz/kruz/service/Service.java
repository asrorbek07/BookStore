package uz.kruz.service;

import uz.kruz.dto.BaseDTO;

import java.util.List;
import java.util.Optional;

public interface Service<D extends BaseDTO, ID> {

    D register(D dto);

    Optional<D> findById(ID id);

    List<D> findAll();

    boolean removeById(ID id);

    D modify(D dto);

    long count();
}
