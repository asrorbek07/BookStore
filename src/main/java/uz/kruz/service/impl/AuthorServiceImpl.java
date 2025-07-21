package uz.kruz.service.impl;

import uz.kruz.domain.Author;
import uz.kruz.domain.User;
import uz.kruz.dto.AuthorDTO;
import uz.kruz.repository.AuthorRepository;
import uz.kruz.repository.BookRepository;
import uz.kruz.service.AuthorService;
import uz.kruz.util.StringUtil;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.EntityNotFoundException;
import uz.kruz.util.exceptions.ServiceException;


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
        if (dto == null) {
            throw new ServiceException("AuthorDTO is null");
        }
        Validator.validateString(dto.getFullName(),"FulName");
        authorRepository.retrieveByName(dto.getFullName());

        if (dto.getFullName()!=null) {
            Validator.validateString(dto.getFullName(),"FullName");
        }

        Author author = Author.builder()
                .fullName(dto.getFullName())
                .build();


        return authorRepository.create(author);
    }

    @Override
    public Optional<Author> findById(Integer id) {
        Validator.validateId(id);

        return authorRepository.retrieveById(id);


    }

    @Override
    public List<Author> findAll() {
        return authorRepository.retrieveAll();

    }

    @Override
    public boolean removeById(Integer id) {
        Validator.validateId(id);
        if (authorRepository.retrieveById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format("Author with ID '%d' not found", id));
        }
        return authorRepository.deleteById(id);
    }

    @Override
    public Author modify(AuthorDTO dto, Integer id) {
        Validator.validateId(id);

        Author author = authorRepository.retrieveById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Author with ID '%d' not found", id)));
        boolean modified = false;
        if(dto.getFullName()!=null){
            Validator.validateString(dto.getFullName(),"FullName");
            author.setFullName(dto.getFullName());
            modified=true;
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
        Validator.validateString(name,"FullName");

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
