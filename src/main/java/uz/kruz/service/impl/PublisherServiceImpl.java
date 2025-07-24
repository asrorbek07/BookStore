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

    private PublisherRepository publisherRepository = null;

    public PublisherServiceImpl(PublisherRepositoryImpl publisherRepository) {
        this.publisherRepository = this.publisherRepository;
    }

    @Override
    public Publisher register(PublisherDTO dto) {
        if (dto == null) {
            throw new ServiceException("PublisherDTO must not be null");
        }
        Validator.validateString(dto.getName(), "Email");
        Validator.validateString(dto.getPhone(), "Password");
        publisherRepository.findFirstByEmail(dto.getContact_email()   )
                .ifPresent(existing -> {
                    throw new DuplicateEntityException(String.format("User with email '%s' already exists", dto.getContact_email()));
                });
        if (dto.getName() != null) {
            Validator.validateString(dto.getName(), "Full name");
        }
        if (dto.getPhone() != null) {
            Validator.validateString(dto.getPhone(), "Phone number");
        }
        Publisher publisher = Publisher.builder()
                .contactEmail(dto.getContact_email())
                .name(dto.getName())
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

      if(publisherRepository.retrieveById(id).isEmpty()) {
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
    public Optional<Publisher> findByName(String name) {
        Validator.validateString(name, "Email");
        return publisherRepository.retrieveByName(name);
    }

    @Override
    public List<Publisher> findByEmail(String email) {
        Validator.validateString(email,"email");
        return publisherRepository.retrieveByEmail(email);
    }
}
