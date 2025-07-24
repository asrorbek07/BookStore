package uz.kruz.service.impl;

import uz.kruz.domain.Publisher;
import uz.kruz.dto.PublisherDTO;
import uz.kruz.repository.PublisherRepository;
import uz.kruz.repository.impl.PublisherRepositoryImpl;
import uz.kruz.service.PublisherService;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.DuplicateEntityException;
import uz.kruz.util.exceptions.EntityNotFoundException;
import uz.kruz.util.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepositoryImpl publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Publisher register(PublisherDTO dto) {
        if (dto == null) {
            throw new ServiceException("PublisherDTO must not be null");
        }
        Validator.validateString(dto.getName(), "Name");
        publisherRepository.retrieveByName(dto.getName())
                .ifPresent(publisher -> {
                    throw new DuplicateEntityException(String.format("Publisher with name '%s' already exists", dto.getName()));
                });
        if (dto.getContactEmail() != null) {
            Validator.validateString(dto.getContactEmail(), "Contact Email");
            if (publisherRepository.existsByContactEmail(dto.getContactEmail())) {
                throw new DuplicateEntityException(String.format("Publisher with contact email '%s' already exists", dto.getContactEmail()));
            }
        }
        if (dto.getPhone() != null) {
            Validator.validateString(dto.getPhone(), "Phone");
            if (publisherRepository.existsByPhone(dto.getPhone())) {
                throw new DuplicateEntityException(String.format("Publisher with phone '%s' already exists", dto.getPhone()));
            }
        }
        Publisher publisher = Publisher.builder()
                .name(dto.getName())
                .contactEmail(dto.getContactEmail())
                .phone(dto.getPhone())
                .build();
        return publisherRepository.create(publisher);
    }

    @Override
    public Optional<Publisher> findById(Integer id) {
        Validator.validateId(id);
        return publisherRepository.retrieveById(id);
    }

    @Override
    public List<Publisher> findAll() {
        return publisherRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        Validator.validateId(id);

        if (publisherRepository.retrieveById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format("Publisher with id '%s' not found", id));
        }

        return publisherRepository.deleteById(id);
    }

    @Override
    public Publisher modify(PublisherDTO dto, Integer id) {
        Validator.validateId(id);

        Publisher publisher = publisherRepository.retrieveById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Publisher with ID '%d' not found", id)));

        boolean modified = false;

        if (dto.getName() != null) {
            Validator.validateString(dto.getName(), "Full name");
            publisher.setName(dto.getName());
            modified = true;
        }

        if (dto.getPhone() != null) {
            Validator.validateString(dto.getPhone(), "Phone number");
            publisher.setPhone(dto.getPhone());
            modified = true;
        }

        if (!modified) {
            throw new ServiceException(String.format("No fields provided for update"));
        }
        return publisherRepository.update(publisher);
    }

    @Override
    public long count() {
        return publisherRepository.count();
    }

    @Override
    public boolean existsById(Integer integer) {
        return publisherRepository.existsById(integer);
    }

    @Override
    public Publisher findByName(String name) {
        Validator.validateString(name, "Name");
        return publisherRepository.retrieveByName(name).orElseThrow(() ->
                new EntityNotFoundException(String.format("Publisher with name '%s' not found", name)));
    }

    @Override
    public List<Publisher> findByEmail(String email) {
        Validator.validateString(email, "email");
        return publisherRepository.retrieveByEmail(email);
    }
}
