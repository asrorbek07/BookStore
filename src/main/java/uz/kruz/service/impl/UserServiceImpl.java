package uz.kruz.service.impl;

import uz.kruz.domain.User;
import uz.kruz.domain.vo.UserRole;
import uz.kruz.dto.UserDTO;
import uz.kruz.repository.UserRepository;
import uz.kruz.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(UserDTO dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<User> findById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean removeById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public User modify(UserDTO dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<User> findByName(String name) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<User> findByRole(UserRole role) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
