package uz.kruz.service.impl;

import uz.kruz.domain.Book;
import uz.kruz.dto.BookDTO;
import uz.kruz.repository.BookRepository;
import uz.kruz.repository.CategoryRepository;
import uz.kruz.repository.PublisherRepository;
import uz.kruz.service.BookService;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Book register(BookDTO dto) {
        if (dto == null) {
            throw new ServiceException("BookDTO must not be null");
        }
        if (dto.getTitle() != null) {
            Validator.validateString(dto.getTitle(), "Title");
        }
        Validator.validateBigDecimal(dto.getPrice(), "Price");
        Validator.validateInteger(dto.getStock(), "Stock");
        Validator.validateInteger(dto.getPublishedYear(), "Published Year");

        Validator.validateString(dto.getIsbn(), "ISBN");

        Validator.validateInteger(dto.getCategoryId(), "Category ID");
        Validator.validateInteger(dto.getPublisherId(), "Publisher ID");
        if (bookRepository.existsByIsbn(dto.getIsbn())) {

            throw new RuntimeException(String.format("Book with ISBN %s already exists", dto.getIsbn()));
        }
        if (!categoryRepository.existsById(dto.getCategoryId())) {
            throw new RuntimeException(String.format("Category with ID %s does not exist", dto.getCategoryId()));
        }

        if (!publisherRepository.existsById(dto.getPublisherId())) {
            throw new RuntimeException(String.format("Publisher with ID %s does not exist", dto.getPublisherId()));
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
    public boolean existsById(Integer integer) {
        return false;
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