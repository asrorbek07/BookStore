package uz.kruz.service;

import uz.kruz.domain.Publisher;
import uz.kruz.dto.PublisherDTO;

import java.util.List;
import java.util.Optional;

public interface PublisherService extends Service<PublisherDTO, Publisher, Integer> {

    Optional<Publisher> findByName(String name);

    List<Publisher> findByEmail(String email);
}
