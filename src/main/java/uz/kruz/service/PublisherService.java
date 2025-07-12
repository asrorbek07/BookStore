package uz.kruz.service;

import uz.kruz.dto.BaseDTO;

import java.util.List;
import java.util.Optional;

public interface PublisherService<D extends BaseDTO> extends Service<D, Integer> {

    Optional<D> findByName(String name);

    List<D> findByEmail(String email);
}
