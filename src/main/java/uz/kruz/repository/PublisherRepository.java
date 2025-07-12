package uz.kruz.repository;

import uz.kruz.domain.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends Repository<Publisher, Integer> {

    Optional<Publisher> retrieveByName(String name);

    List<Publisher> retrieveByEmail(String email);
}
