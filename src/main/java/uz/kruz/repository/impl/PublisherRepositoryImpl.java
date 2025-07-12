package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Publisher;
import uz.kruz.repository.PublisherRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PublisherRepositoryImpl implements PublisherRepository {

    private final Connection connection;

    public PublisherRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Publisher create(Publisher entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Publisher> retrieveById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Publisher> retrieveAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean deleteById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Publisher update(Publisher entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Publisher> retrieveByName(String name) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Publisher> retrieveByEmail(String email) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
