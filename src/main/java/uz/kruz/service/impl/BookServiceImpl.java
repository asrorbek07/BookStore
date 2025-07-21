package uz.kruz.service.impl;

import uz.kruz.domain.Book;
import uz.kruz.dto.BookDTO;
import uz.kruz.repository.BookRepository;
import uz.kruz.repository.CategoryRepository;
import uz.kruz.repository.PublisherRepository;
import uz.kruz.service.BookService;
import uz.kruz.util.StringUtil;

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
        if ((dto.getPrice() == null) &&
                dto.getIsbn() == null && dto.getTitle() == null && dto.getStock() == null && dto.getCategoryId() == null && dto.getPublishedYear() == null)
            throw new NullPointerException("BookDTO fields are null");

        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new RuntimeException(String.format("Book with ISBN %s already exists", dto.getIsbn()));
        }
        if (!categoryRepository.existsById(dto.getCategoryId())) {
            throw new RuntimeException(String.format("Category with ID %s does not exist", dto.getCategoryId()));
        }

            if (StringUtil.isEmpty(dto.getTitle())) {
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