package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.User;
import uz.kruz.domain.vo.UserRole;
import uz.kruz.repository.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private final Connection connection;

    public UserRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public User create(User entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<User> retrieveById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<User> retrieveAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean deleteById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public User update(User entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<User> retrieveByEmail(String email) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<User> retrieveByName(String name) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<User> retrieveByRole(UserRole role) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}