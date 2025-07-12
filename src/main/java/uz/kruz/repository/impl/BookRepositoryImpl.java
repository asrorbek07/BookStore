package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Book;
import uz.kruz.repository.BookRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {

    private final Connection connection;

    public BookRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Book create(Book entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Book> retrieveById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> retrieveAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean deleteById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Book update(Book entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Book> retrieveByIsbn(String isbn) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> retrieveByTitle(String title) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> retrieveByCategoryId(Integer categoryId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> retrieveByPublisherId(Integer publisherId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> retrieveByAuthorId(Integer authorId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> retrieveByStockLessThan(Integer amount) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
