package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.BookAuthor;
import uz.kruz.repository.BookAuthorRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BookAuthorRepositoryImpl implements BookAuthorRepository {

    private final Connection connection;

    public BookAuthorRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public BookAuthor create(BookAuthor entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<BookAuthor> retrieveById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<BookAuthor> retrieveAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean deleteById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public BookAuthor update(BookAuthor entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<BookAuthor> retrieveByBookId(Integer bookId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<BookAuthor> retrieveByAuthorId(Integer authorId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<BookAuthor> retrieveByBookIdAndAuthorId(Integer bookId, Integer authorId) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
