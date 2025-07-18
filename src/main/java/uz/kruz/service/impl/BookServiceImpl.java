package uz.kruz.service.impl;

import uz.kruz.dto.BaseDTO;
import uz.kruz.repository.BookRepository;
import uz.kruz.service.BookService;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl<D extends BaseDTO> implements BookService<D> {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {


        this.bookRepository = bookRepository;
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
    public Optional<D> findByIsbn(String isbn) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByTitle(String title) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByCategoryId(Integer categoryId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByPublisherId(Integer publisherId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByAuthorId(Integer authorId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByStockLessThan(Integer amount) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}