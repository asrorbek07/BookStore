package uz.kruz.service;

import uz.kruz.domain.Publisher;
import uz.kruz.dto.PublisherDTO;

import java.util.List;

public interface PublisherService extends Service<PublisherDTO, Publisher, Integer> {

    Publisher findByName(String name);

    List<Publisher> findByEmail(String email);
}
