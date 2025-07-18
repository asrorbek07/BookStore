package uz.kruz.service.impl;

import uz.kruz.domain.Book;
import uz.kruz.dto.BookDTO;
import uz.kruz.repository.BookRepository;
import uz.kruz.service.BookService;
import uz.kruz.util.StringUtil;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book register(BookDTO dto) {
        if (StringUtil.isEmpty(dto.getIsbn())&&
        bookRepository.retrieveByIsbn(dto.getIsbn()).isPresent()
        ){

        }


        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Book> findById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean removeById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Book modify(BookDTO dto, Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> findByTitle(String title) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> findByCategoryId(Integer categoryId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> findByPublisherId(Integer publisherId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> findByAuthorId(Integer authorId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> findByStockLessThan(Integer amount) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}