package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Author;
import uz.kruz.repository.AuthorRepository;
import uz.kruz.util.exceptions.DatabaseUnavailableException;
import uz.kruz.util.exceptions.DuplicateRowException;
import uz.kruz.util.exceptions.RowNotFoundException;
import uz.kruz.util.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {

    private static final String INSERT = "INSERT INTO authors (full_name) VALUES (?)";
    private static final String SELECT_BY_ID = "SELECT * FROM authors WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM authors";
    private static final String RETRIEVE_BY_NAME = "SELECT * FROM authors WHERE full_name LIKE ?";
    private static final String RETRIEVE_BY_BOOK_ID =
            "SELECT a.* FROM authors a " +
                    "JOIN book_authors ba ON a.id = ba.author_id " +
                    "WHERE ba.book_id = ?";
    private static final String COUNT = "SELECT COUNT(*) FROM authors";
    private static final String UPDATE = "UPDATE authors SET full_name = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM authors WHERE id = ?";

    private final Connection connection;

    public AuthorRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new DatabaseUnavailableException("Could not establish database connection", e);
        }
    }

    @Override
    public Author create(Author entity) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getFullName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return retrieveById(entity.getId()).orElseThrow(() ->
                new RepositoryException("Author not found after creation: " + entity.getFullName()));
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateRowException("Author already exists: " + entity.getFullName(), e);
        } catch (SQLException e) {
            throw new RepositoryException("Error creating author: " + entity, e);
        }
    }

    @Override
    public Optional<Author> retrieveById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving author by ID: " + id, e);
        }
    }

    @Override
    public List<Author> retrieveAll() {
        List<Author> authors = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                authors.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving all authors", e);
        }
        return authors;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new RowNotFoundException("Author not found for deletion with ID: " + id);
            }
            return true;
        } catch (SQLException e) {
            throw new RepositoryException("Error deleting author with ID: " + id, e);
        }
    }

    @Override
    public Author update(Author entity) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, entity.getFullName());
            ps.setInt(2, entity.getId());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new RowNotFoundException("Author not found for update with ID: " + entity.getId());
            }
            return entity;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateRowException("Another author with the same name already exists: " + entity.getFullName(), e);
        } catch (SQLException e) {
            throw new RepositoryException("Error updating author: " + entity, e);
        }
    }

    @Override
    public long count() {
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(COUNT);
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RepositoryException("Error counting authors", e);
        }
    }

    @Override
    public List<Author> retrieveByName(String name) {
        List<Author> authors = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(RETRIEVE_BY_NAME)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                authors.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving authors by name: " + name, e);
        }
        return authors;
    }

    @Override
    public List<Author> retrieveByBookId(Integer bookId) {
        List<Author> authors = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(RETRIEVE_BY_BOOK_ID)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                authors.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving authors by book ID: " + bookId, e);
        }
        return authors;
    }

    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RepositoryException("Error checking if author exists by ID: " + id, e);
        }
    }

    private Author mapRow(ResultSet rs) throws SQLException {
        return Author.builder()
                .id(rs.getInt("id"))
                .fullName(rs.getString("full_name"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }
}
