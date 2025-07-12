package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Author;
import uz.kruz.repository.AuthorRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final Connection connection;

    public AuthorRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Author create(Author entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Author> retrieveById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Author> retrieveAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean deleteById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Author update(Author entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Author> retrieveByName(String name) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Author> retrieveByBookId(Integer bookId) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
