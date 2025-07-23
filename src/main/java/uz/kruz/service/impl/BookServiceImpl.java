package uz.kruz.service.impl;

import uz.kruz.domain.Book;
import uz.kruz.dto.BookDTO;
import uz.kruz.repository.*;
import uz.kruz.service.BookService;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.EntityNotFoundException;
import uz.kruz.util.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private  CategoryRepository categoryRepository;
    private  PublisherRepository publisherRepository;
    private  AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, PublisherRepository publisherRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
    }

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
        Book book = Book.builder()
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .publishedYear(dto.getPublishedYear())
                .categoryId(dto.getCategoryId())
                .publisherId(dto.getPublisherId())
                .build();
        return bookRepository.create(book);
    }

    @Override
    public Optional<Book> findById(Integer id) {
        Validator.validateInteger(id, "Book ID");
        return bookRepository.retrieveById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        Validator.validateInteger(id, "Book ID");
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Book modify(BookDTO dto, Integer id) {
        Validator.validateInteger(id, "Book ID");
        Book book = bookRepository.retrieveById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Book with ID %s does not exist", id)));

        boolean modified = false;
        if (dto.getTitle() != null) {
            Validator.validateString(dto.getTitle(), "Title");
            book.setTitle(dto.getTitle());
            modified = true;
        }
        if (dto.getPrice() != null) {
            Validator.validateBigDecimal(dto.getPrice(), "Price");
            book.setPrice(dto.getPrice());
            modified = true;
        }
        if (dto.getIsbn() != null) {
            Validator.validateString(dto.getIsbn(), "ISBN");
            if (bookRepository.existsByIsbn(dto.getIsbn())) {
                throw new RuntimeException(String.format("Book with ISBN %s already exists", dto.getIsbn()));
            }
            book.setIsbn(dto.getIsbn());
            modified = true;
        }
        if (dto.getCategoryId() != null) {
            Validator.validateInteger(dto.getCategoryId(), "Category ID");
            if (!categoryRepository.existsById(dto.getCategoryId())) {
                throw new RuntimeException(String.format("Category with ID %s does not exist", dto.getCategoryId()));
            }
            book.setCategoryId(dto.getCategoryId());
            modified = true;
        }
        if (dto.getPublisherId() != null) {
            Validator.validateInteger(dto.getPublisherId(), "Publisher ID");
            if (!publisherRepository.existsById(dto.getPublisherId())) {
                throw new RuntimeException(String.format("Publisher with ID %s does not exist", dto.getPublisherId()));
            }
            book.setPublisherId(dto.getPublisherId());
            modified = true;
        }
        if (!modified) {
            throw new RuntimeException(String.format("Book with ID %s does not exist", id));
        }
        return bookRepository.update(book);

    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public boolean existsById(Integer integer) {
        Validator.validateInteger(integer, "Book ID");
        return bookRepository.existsById(integer);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        Validator.validateString(isbn, "ISBN");
        return bookRepository.retrieveByIsbn(isbn);
    }

    @Override
    public List<Book> findByTitle(String title) {
        Validator.validateString(title, "Title");
        return bookRepository.retrieveByTitle(title);
    }

    @Override
    public List<Book> findByCategoryId(Integer categoryId) {
        Validator.validateInteger(categoryId, "Category ID");
        if (!categoryRepository.existsById(categoryId)){
            throw new RuntimeException(String.format("Category with ID %s does not exist", categoryId));
        }
        return bookRepository.retrieveByCategoryId(categoryId);
    }

    @Override
    public List<Book> findByPublisherId(Integer publisherId) {
        Validator.validateInteger(publisherId, "Publisher ID");
        if (!publisherRepository.existsById(publisherId)){
            throw new RuntimeException(String.format("Publisher with ID %s does not exist", publisherId));
        }
        return bookRepository.retrieveByPublisherId(publisherId);
    }

    @Override
    public List<Book> findByAuthorId(Integer authorId) {
        Validator.validateInteger(authorId, "Author ID");
        if (!authorRepository.existsById(authorId)){
            throw new RuntimeException(String.format("Author with ID %s does not exist", authorId));
        }
        return bookRepository.retrieveByAuthorId(authorId);
    }

    @Override
    public List<Book> findByStockLessThan(Integer amount) {
        Validator.validateInteger(amount, "Stock");
        return bookRepository.retrieveByStockLessThan(amount);    }
}