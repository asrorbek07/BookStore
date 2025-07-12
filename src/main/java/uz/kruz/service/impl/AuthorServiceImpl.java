package uz.kruz.service.impl;

import uz.kruz.dto.BaseDTO;
import uz.kruz.repository.AuthorRepository;
import uz.kruz.service.AuthorService;

import java.util.List;
import java.util.Optional;

public class AuthorServiceImpl<D extends BaseDTO> implements AuthorService<D> {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
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
    public List<D> findByName(String name) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByBookId(Integer bookId) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
