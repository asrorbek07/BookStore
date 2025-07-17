package uz.kruz.service.impl;

import uz.kruz.domain.Author;
import uz.kruz.dto.AuthorDTO;
import uz.kruz.repository.AuthorRepository;
import uz.kruz.service.AuthorService;

import java.util.List;
import java.util.Optional;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author register(AuthorDTO dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Author> findById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Author> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean removeById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Author modify(AuthorDTO dto, Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Author> findByName(String name) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Author> findByBookId(Integer bookId) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
