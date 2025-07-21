package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.User;
import uz.kruz.domain.vo.UserRole;
import uz.kruz.repository.UserRepository;
import uz.kruz.util.exceptions.DatabaseUnavailableException;
import uz.kruz.util.exceptions.RowNotFoundException;
import uz.kruz.util.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private static final String insert = "INSERT INTO users (fullName, email, password, phone, role) VALUES (?, ?, ?, ?, ?)";
    private static final String selectById = "SELECT * FROM users WHERE id = ?";
    private static final String selectAll = "SELECT * FROM users";
    private static final String deleteById = "DELETE FROM users WHERE id = ?";
    private static final String update = "UPDATE users SET fullName = ?, email = ?, password = ?, phone = ?, role = ? WHERE id = ?";
    private static final String count = "SELECT COUNT(*) FROM users";
    private static final String selectByEmail = "SELECT * FROM users WHERE email = ?";
    private static final String selectByName = "SELECT * FROM users WHERE fullName LIKE ?";
    private static final String selectByRole = "SELECT * FROM users WHERE role = ?";

    private final Connection connection;

    public UserRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new DatabaseUnavailableException("Failed to initialize database connection", e);
        }
    }

    @Override
    public User create(User user) {
        try (PreparedStatement ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole().name());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            throw new RepositoryException("Error creating user", e);
        }
    }

    @Override
    public Optional<User> retrieveById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(selectById)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving user by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> retrieveAll() {
        List<User> users = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectAll);
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving all users", e);
        }
        return users;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(deleteById)) {
            ps.setInt(1, id);
            int deleted = ps.executeUpdate();
            if (deleted == 0) {
                throw new RowNotFoundException("User not found for ID: " + id);
            }
            return true;
        } catch (SQLException e) {
            throw new RepositoryException("Error deleting user", e);
        }
    }

    @Override
    public User update(User user) {
        try (PreparedStatement ps = connection.prepareStatement(update)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole().name());
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new RowNotFoundException("User not found for ID: " + user.getId());
            }
            return user;
        } catch (SQLException e) {
            throw new RepositoryException("Error updating user", e);
        }
    }

    @Override
    public long count() {
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(count);
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error counting users", e);
        }
        return 0;
    }

    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(selectById)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error checking existence for user id: " + id, e);
        }
    }

    @Override
    public Optional<User> retrieveByEmail(String email) {
        try (PreparedStatement ps = connection.prepareStatement(selectByEmail)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving user by email", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> retrieveByName(String name) {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(selectByName)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving users by name", e);
        }
        return users;
    }

    @Override
    public List<User> retrieveByRole(UserRole role) {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(selectByRole)) {
            ps.setString(1, role.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving users by role", e);
        }
        return users;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .fullName(rs.getString("fullName"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .phone(rs.getString("phone"))
                .role(UserRole.valueOf(rs.getString("role")))
                .build();
    }
}
