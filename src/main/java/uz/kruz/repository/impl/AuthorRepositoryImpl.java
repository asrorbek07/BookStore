package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Author;
import uz.kruz.repository.AuthorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {

    private Connection connection;
    private final String INSERT = "INSERT INTO author (full_name) VALUES (?)";
    private final String SELECT_BY_ID = "SELECT * FROM author WHERE id = ?";
    private final String SELECT_ALL = "SELECT * FROM author";
    private final String RETRIEVE_BY_NAME = "SELECT * FROM author WHERE full_name LIKE ?";
    private final String RETRIEVE_BY_BOOK_ID = "...";
    private final String COUNT = "SELECT COUNT(*) FROM author";
    private final String UPDATE = "UPDATE author SET full_name = ? WHERE id = ?";
    private final String DELETE_BY_ID = "DELETE FROM author WHERE id = ?";

    public AuthorRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
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
            return entity;

        } catch (SQLException e) {
            throw new RuntimeException("Error creating entity", e);
        }

    }

    @Override
    public Optional<Author> retrieveById(Integer id) {

        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving entity", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Author> retrieveAll() {
        List<Author> authors = new ArrayList<>();

        try (Statement stms = connection.createStatement()) {
            ResultSet rs = stms.executeQuery(SELECT_ALL);
            while (rs.next()) {
                authors.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving entity", e);
        }
        return authors;
    }


    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting entity", e);
        }
    }

    @Override
    public Author update(Author entity) {

        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, entity.getFullName());
            ps.setInt(2, entity.getId());
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating entity", e);
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
            throw new RuntimeException("Error counting entity", e);
        }
        return 0;
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
            throw new RuntimeException("Error retrieving entity", e);
        }

        return authors;
    }

    @Override
    public List<Author> retrieveByBookId(Integer bookId) {
        List<Author> authors = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(RETRIEVE_BY_BOOK_ID)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                authors.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving entity", e);
        }
        return authors;
    }

    private Author mapRow(ResultSet rs) throws SQLException {
        return Author.builder()
                .id(rs.getInt("id"))
                .fullName(rs.getString("full_name"))
                .build();
    }

    public static void main(String[] args) {
        AuthorRepository authorRepository = new AuthorRepositoryImpl();
        Author authorToUpdate = Author.builder()
                .fullName("Shokirjon")
                .id(1)
                .build();
        authorToUpdate.setId(1); // mavjud muallif ID-si
        authorToUpdate.setFullName("Yangi Muallif Ismi");

        Author updatedAuthor = authorRepository.update(authorToUpdate);
        System.out.println("Updated Author: " + updatedAuthor);
    }
}

