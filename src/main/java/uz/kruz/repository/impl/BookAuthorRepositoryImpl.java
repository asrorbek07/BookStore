package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.BookAuthor;
import uz.kruz.util.exceptions.DatabaseUnavailableException;
import uz.kruz.util.exceptions.RowNotFoundException;
import uz.kruz.util.exceptions.RepositoryException;
import uz.kruz.repository.BookAuthorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookAuthorRepositoryImpl implements BookAuthorRepository {

    private final Connection connection;

    private static final String INSERT = "INSERT INTO book_authors (book_id, author_id) VALUES (?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM book_authors WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM book_authors";
    private static final String DELETE_BY_ID = "DELETE FROM book_authors WHERE id = ?";
    private static final String UPDATE = "UPDATE book_authors SET book_id = ?, author_id = ? WHERE id = ?";
    private static final String COUNT = "SELECT COUNT(*) FROM book_authors";
    private static final String SELECT_BY_BOOK_ID = "SELECT * FROM book_authors WHERE book_id = ?";
    private static final String SELECT_BY_AUTHOR_ID = "SELECT * FROM book_authors WHERE author_id = ?";
    private static final String SELECT_BY_BOOK_AND_AUTHOR = "SELECT * FROM book_authors WHERE book_id = ? AND author_id = ?";

    public BookAuthorRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new DatabaseUnavailableException("Failed to establish DB connection", e);
        }
    }

    @Override
    public BookAuthor create(BookAuthor entity) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getBookId());
            ps.setInt(2, entity.getAuthorId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return entity;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RepositoryException("Duplicate book-author pair: " + entity, e);
        } catch (SQLException e) {
            throw new RepositoryException("Failed to create book-author record: " + entity, e);
        }
    }

    @Override
    public Optional<BookAuthor> retrieveById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving book-author by ID: " + id, e);
        }
    }

    @Override
    public List<BookAuthor> retrieveAll() {
        List<BookAuthor> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving all book-author relationships", e);
        }
        return list;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new RowNotFoundException("No book-author found for deletion with ID: " + id);
            }
            return true;
        } catch (SQLException e) {
            throw new RepositoryException("Error deleting book-author with ID: " + id, e);
        }
    }

    @Override
    public BookAuthor update(BookAuthor entity) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setInt(1, entity.getBookId());
            ps.setInt(2, entity.getAuthorId());
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new RowNotFoundException("No book-author found for update with ID: " + entity.getId());
            }
            return entity;
        } catch (SQLException e) {
            throw new RepositoryException("Error updating book-author: " + entity, e);
        }
    }

    @Override
    public long count() {
        try (PreparedStatement stmt = connection.prepareStatement(COUNT)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RepositoryException("Error counting book-author entries", e);
        }
    }

    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RepositoryException("Error checking existence for book-author ID: " + id, e);
        }
    }

    @Override
    public List<BookAuthor> retrieveByBookId(Integer bookId) {
        List<BookAuthor> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_BOOK_ID)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving by book ID: " + bookId, e);
        }
        return list;
    }

    @Override
    public List<BookAuthor> retrieveByAuthorId(Integer authorId) {
        List<BookAuthor> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_AUTHOR_ID)) {
            ps.setInt(1, authorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving by author ID: " + authorId, e);
        }
        return list;
    }

    @Override
    public Optional<BookAuthor> retrieveByBookIdAndAuthorId(Integer bookId, Integer authorId) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_BOOK_AND_AUTHOR)) {
            ps.setInt(1, bookId);
            ps.setInt(2, authorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving by bookId + authorId", e);
        }
        return Optional.empty();
    }

    private BookAuthor mapRow(ResultSet rs) throws SQLException {
        return BookAuthor.builder()
                .id(rs.getInt("id"))
                .bookId(rs.getInt("book_id"))
                .authorId(rs.getInt("author_id"))
                .build();
    }

    public static void main(String[] args) {
        BookAuthorRepository repo = new BookAuthorRepositoryImpl();
        System.out.println("Exists by ID 5: " + repo.existsById(5));
    }
}
