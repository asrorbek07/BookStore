package uz.kruz.service.impl;

import uz.kruz.domain.User;
import uz.kruz.domain.vo.UserRole;
import uz.kruz.dto.UserDTO;
import uz.kruz.repository.UserRepository;
import uz.kruz.service.UserService;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.DuplicateEntityException;
import uz.kruz.util.exceptions.EntityNotFoundException;
import uz.kruz.util.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(UserDTO dto) {
        if (dto == null) {
            throw new ServiceException("UserDTO must not be null");
        }
        Validator.validateString(dto.getEmail(), "Email");
        Validator.validateString(dto.getPassword(), "Password");
        userRepository.retrieveByEmail(dto.getEmail())
                .ifPresent(existing -> {
                    throw new DuplicateEntityException(String.format("User with email '%s' already exists", dto.getEmail()));
                });
        if (dto.getFullName() != null) {
            Validator.validateString(dto.getFullName(), "Full name");
        }
        if (dto.getPhoneNumber() != null) {
            Validator.validateString(dto.getPhoneNumber(), "Phone number");
        }
        UserRole role = dto.getRole() != null ? dto.getRole() : UserRole.CUSTOMER;
        User user = User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .fullName(dto.getFullName())        // may be null
                .phone(dto.getPhoneNumber())        // may be null
                .role(role)
                .build();
        return userRepository.create(user);
    }


    @Override
    public Optional<User> findById(Integer id) {
        Validator.validateId(id);
        return userRepository.retrieveById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        Validator.validateId(id);

        if (userRepository.retrieveById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format("User with ID '%d' not found", id));
        }

        return userRepository.deleteById(id);
    }

    @Override
    public User modify(UserDTO dto, Integer id) {
        Validator.validateId(id);

        User user = userRepository.retrieveById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with ID '%d' not found", id)));

        boolean modified = false;

        if (dto.getFullName() != null) {
            Validator.validateString(dto.getFullName(), "Full name");
            user.setFullName(dto.getFullName());
            modified = true;
        }

        if (dto.getPhoneNumber() != null) {
            Validator.validateString(dto.getPhoneNumber(), "Phone number");
            user.setPhone(dto.getPhoneNumber());
            modified = true;
        }

        if (dto.getPassword() != null) {
            Validator.validateString(dto.getPassword(), "Password");
            user.setPassword(dto.getPassword());
            modified = true;
        }

        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
            modified = true;
        }

        if (!modified) {
            throw new ServiceException("No fields provided for update");
        }

        return userRepository.update(user);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public boolean existsById(Integer integer) {
        return userRepository.existsById(integer);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Validator.validateString(email, "Email");
        return userRepository.retrieveByEmail(email);
    }

    @Override
    public List<User> findByName(String name) {
        Validator.validateString(name, "Name");
        return userRepository.retrieveByName(name);
    }

    @Override
    public List<User> findByRole(UserRole role) {
        if (role == null) {
            throw new ServiceException("Role must not be null");
        }
        return userRepository.retrieveByRole(role);
    }
}
