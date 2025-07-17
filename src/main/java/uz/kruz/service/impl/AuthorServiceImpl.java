package uz.kruz.service.impl;

import uz.kruz.domain.Author;
import uz.kruz.domain.User;
import uz.kruz.dto.AuthorDTO;
import uz.kruz.repository.AuthorRepository;
import uz.kruz.service.AuthorService;
import uz.kruz.util.StringUtil;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author register(AuthorDTO dto) {
        if (StringUtil.isEmpty(dto.getFullName())) {
            throw new IllegalArgumentException("full name must not be empty");
        }
        if(authorRepository.retrieveByName(dto.getFullName())!=null){
            throw new RuntimeException(String.format("Author with name %s already exists", dto.getFullName()));
        }

        Author author = Author.builder()
                .fullName(dto.getFullName())
                .build();


        return authorRepository.create(author);
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
        Author author = authorRepository.retrieveById(id).orElseThrow(()-> new RuntimeException(String.format("Author with id %d does not exists", id)));
        if(dto.getFullName()!=null && ! StringUtil.isEmpty(dto.getFullName())){
            author.setFullName(dto.getFullName());
        }
        return authorRepository.update(author);
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
