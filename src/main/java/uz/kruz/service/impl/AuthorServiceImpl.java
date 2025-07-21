package uz.kruz.service.impl;

import uz.kruz.domain.Author;
import uz.kruz.domain.User;
import uz.kruz.dto.AuthorDTO;
import uz.kruz.repository.AuthorRepository;
import uz.kruz.repository.BookRepository;
import uz.kruz.service.AuthorService;
import uz.kruz.util.StringUtil;


import java.sql.*;
import java.util.*;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
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


        return authorRepository.retrieveById(id);


    }

    @Override
    public List<Author> findAll() {
        return authorRepository.retrieveAll();

    }

    @Override
    public boolean removeById(Integer id) {

        return authorRepository.deleteById(id);
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
        return authorRepository.count();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }


    @Override
    public List<Author> findByName(String name) {
        if (name==null|| StringUtil.isEmpty(name)) {
            throw new IllegalArgumentException("name must not be empty or null");

        }
        return authorRepository.retrieveByName(name);
    }


    @Override
    public List<Author> findByBookId(Integer bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new RuntimeException(String.format("Book with id %d does not exists", bookId));
        }
        return authorRepository.retrieveByBookId(bookId);
    }

}
