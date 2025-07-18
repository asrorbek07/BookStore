package uz.kruz.service.impl;

import jdk.jshell.spi.ExecutionControl;
import uz.kruz.domain.User;
import uz.kruz.domain.vo.UserRole;
import uz.kruz.dto.UserDTO;
import uz.kruz.repository.UserRepository;
import uz.kruz.service.UserService;
import uz.kruz.util.StringUtil;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(UserDTO dto) {
        if (StringUtil.isEmpty(dto.getEmail())) {
            throw new IllegalArgumentException("email must not be empty");
        }
        if (userRepository.retrieveByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException(String.format("User with email %s already exists", dto.getEmail()));
        }
        if (StringUtil.isEmpty(dto.getPassword()) ||
                StringUtil.isEmpty(dto.getFullName()) ||
                StringUtil.isEmpty(dto.getPhoneNumber())) {
            throw new IllegalArgumentException("fields must not be empty");
        }

        User user = User.builder()
                .phone(dto.getPhoneNumber())
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
        return userRepository.create(user);

    }

    @Override
    public Optional<User> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return userRepository.retrieveById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        return userRepository.deleteById(id);
    }

    @Override
    public User modify(UserDTO dto, Integer id) {
        User user = userRepository.retrieveById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        if (dto.getFullName() != null && !StringUtil.isEmpty(dto.getFullName())) {
            user.setFullName(dto.getFullName());
        }

        return userRepository.update(user);

    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (StringUtil.isEmpty(email)) {
            return userRepository.retrieveByEmail(email);
        } else
            throw new RuntimeException(String.format("User with email %s already exists", email));
    }

    @Override
    public List<User> findByName(String name) {
        if (StringUtil.isEmpty(name)) {
            return userRepository.retrieveByName(name);
        }
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<User> findByRole(UserRole role) {
        if (StringUtil.isEmpty(role.name()))
            return userRepository.retrieveByRole(role);
        throw new UnsupportedOperationException("Method not implemented");
    }
}
