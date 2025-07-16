package uz.kruz.repository.impl;

import org.mariadb.jdbc.Statement;
import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.BookAuthor;
import uz.kruz.repository.BookAuthorRepository;

import java.sql.*;
import java.util.ArrayList;
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
        String sql = "INSERT INTO book_authors (book_id, auther_id, created_id, upload_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getAuthorId());
            ps.setTimestamp(2, Timestamp.valueOf(entity.getCreatedAt()));
            ps.setTimestamp(3, Timestamp.valueOf(entity.getUpdatedAt()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create book author", e);
        }
    }

    @Override
    public Optional<BookAuthor> retrieveById(Integer id) {
        String sql = "SELECT * FROM BookAuthor WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BookAuthor bookAuthor = mapRow(rs);
                return Optional.of(bookAuthor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving BookAuthor by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<BookAuthor> retrieveAll() {
        return List.of();
    }

    private BookAuthor mapRow(ResultSet rs) {
        return null;
    }

    @Override
    public List<BookAuthor> retrieveAll(String name) {
        List<BookAuthor> BookAuthors = new ArrayList<>();
        String sql = "SELECT * FROM BookAuthor WHERE Author_name LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookAuthors.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving BookAuthor by name", e);
        }
        return BookAuthors;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM Books WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Books", e);
        }
    }

    @Override
    public BookAuthor update(BookAuthor entity) {
        String sql = "UPDATE BookAuthor SET bookId = ?, authorId = ?, book = ?, author = ? where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(entity.getBookId()));
            ps.setString(2,String.valueOf(entity.getAuthorId()));
            ps.setString(3, String.valueOf(entity.getBook()));
            ps.setString(4,String.valueOf(entity.getAuthor()));
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("throw updating book", e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM book_authors";
        try (Statement stmt = (Statement) connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return rs.getLong(1);
            }
            } catch (SQLException e) {
            throw new RuntimeException("Error counting bookAuthors", e);
        }
        return 0;
    }

    @Override
    public List<BookAuthor> retrieveByBookId(Integer bookId) {
        List<BookAuthor> bookAuthors = new ArrayList<>();
        String sql = "SELECT * FROM BookAuthors WHERE book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookAuthors.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving BookAuthor by bookId", e);
        }
        return bookAuthors;
    }


    @Override
    public List<BookAuthor> retrieveByAuthorId(Integer authorId) {
        List<BookAuthor> bookAuthors = new ArrayList<>();
        String sql = "SELECT * FROM BookAuthors WHERE author_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, authorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookAuthors.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving BookAuthor by AuthorId", e);
        }
        return bookAuthors;
    }

    @Override
    public Optional<BookAuthor> retrieveByBookIdAndAuthorId(Integer bookId, Integer authorId) {
        String sql = "SELECT * FROM BookAuthors WHERE book_id = ? AND author_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ps.setInt(2, authorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs)); // mapRow() -> rs dan BookAuthor obyektini yasaydi
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving BookAuthor by bookId and authorId", e);
        }
        return Optional.empty();
    }
}
