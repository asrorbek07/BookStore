package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Publisher;
import uz.kruz.repository.PublisherRepository;
import uz.kruz.util.exceptions.DatabaseUnavailableException;
import uz.kruz.util.exceptions.RepositoryException;
import uz.kruz.util.exceptions.RowNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublisherRepositoryImpl implements PublisherRepository {
    private static final String INSERT = "INSERT INTO publishers(name, contact_email, phone) VALUES (?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM publishers WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM publishers";
    private static final String DELETE_BY_ID = "DELETE FROM publishers WHERE id = ?";
    private static final String UPDATE = "UPDATE publishers SET name = ?, contact_email = ?, phone = ? WHERE id = ?";
    private static final String COUNT = "SELECT COUNT(*) FROM publishers";
    private static final String SELECT_BY_NAME = "SELECT * FROM publishers WHERE name = ?";
    private static final String EXISTS_BY_CONTACT_EMAIL = "SELECT EXISTS(SELECT 1 FROM publishers WHERE contact_email = ?)";
    private static final String EXISTS_BY_PHONE = "SELECT EXISTS(SELECT 1 FROM publishers WHERE phone = ?)";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM publishers WHERE contact_email = ?";

    private final Connection connection;

    public PublisherRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new DatabaseUnavailableException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Publisher create(Publisher entity) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getContactEmail());
            ps.setString(3, entity.getPhone());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error inserting publisher", e);
        }
        return retrieveById(entity.getId()).orElseThrow(() ->
                new RepositoryException("Failed to retrieve publisher after insertion"));
    }

    @Override
    public Optional<Publisher> retrieveById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving publisher by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Publisher> retrieveAll() {
        List<Publisher> publishers = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                publishers.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving all publishers", e);
        }
        return publishers;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            int deleted = ps.executeUpdate();
            if (deleted == 0) {
                throw new RowNotFoundException("Publisher not found for ID: " + id);
            }
            return true;
        } catch (SQLException e) {
            throw new RepositoryException("Error deleting publisher", e);
        }
    }

    @Override
    public Publisher update(Publisher entity) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getContactEmail());
            ps.setString(3, entity.getPhone());
            ps.setInt(4, entity.getId());
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new RowNotFoundException("Publisher not found for ID: " + entity.getId());
            }
            return entity;
        } catch (SQLException e) {
            throw new RepositoryException("Error updating publisher", e);
        }
    }

    @Override
    public long count() {
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(COUNT);
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error counting publishers", e);
        }
        return 0;
    }

    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RepositoryException("Error checking if publisher exists", e);
        }
    }

    @Override
    public Optional<Publisher> retrieveByName(String name) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_NAME)) {
            ps.setString(1, name );
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving publisher by name", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Publisher> retrieveByEmail(String email) {
        List<Publisher> publishers = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_EMAIL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                publishers.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving publisher by email", e);
        }
        return publishers;
    }

    @Override
    public boolean existsByContactEmail(String contactEmail) {
        try {
            try (PreparedStatement ps = connection.prepareStatement(EXISTS_BY_CONTACT_EMAIL)) {
                ps.setString(1, contactEmail);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error checking if publisher exists by contact email", e);
        }
    }

    @Override
    public boolean existsByPhone(String phone) {
        try {
            try (PreparedStatement ps = connection.prepareStatement(EXISTS_BY_PHONE)) {
                ps.setString(1, phone);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error checking if publisher exists by phone", e);
        }
    }

    private Publisher mapRow(ResultSet rs) throws SQLException {
        return Publisher.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .contactEmail(rs.getString("contact_email"))
                .phone(rs.getString("phone"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }
}
