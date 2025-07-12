package uz.kruz.service.impl;

import uz.kruz.dto.BaseDTO;
import uz.kruz.domain.vo.UserRole;
import uz.kruz.repository.UserRepository;
import uz.kruz.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl<D extends BaseDTO> implements UserService<D> {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public D register(D dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<D> findById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean removeById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public D modify(D dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<D> findByEmail(String email) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByName(String name) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByRole(UserRole role) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
