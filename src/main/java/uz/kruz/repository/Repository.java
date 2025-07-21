package uz.kruz.repository;

import uz.kruz.domain.vo.Entity;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity, ID> {

    T create(T entity);

    Optional<T> retrieveById(ID id);

    List<T> retrieveAll();

    boolean deleteById(ID id);

    T update(T entity);

    long count();

    boolean existsById(ID id);
}
