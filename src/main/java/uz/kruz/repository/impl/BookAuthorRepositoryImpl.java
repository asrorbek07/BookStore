package uz.kruz.repository.impl;

import org.mariadb.jdbc.Statement;
import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.BookAuthor;
import uz.kruz.repository.BookAuthorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.awt.event.PaintEvent.UPDATE;
import static java.nio.file.attribute.AclEntryPermission.DELETE;
import static javax.swing.text.html.HTML.Tag.SELECT;

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
        String sql = "INSERT INTO book_authors (book_id, author_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getBookId());
            ps.setInt(2, entity.getAuthorId());
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create book_authors", e);
        }
    }


    @Override
    public Optional<BookAuthor> retrieveById(Integer id) {
        String sql = "SELECT * FROM book_authors WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book_authors by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<BookAuthor> retrieveAll() {
        List<BookAuthor> list = new ArrayList<>();
        String sql = "SELECT * FROM book_authors";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all book_authors", e);
        }
        return list;
    }

    @Override
    public List<BookAuthor> retrieveAll(String name) {
        List<BookAuthor> list = new ArrayList<>();
        String sql = "SELECT * from book_author";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book_authors by author name", e);
        }
        return list;
    }


    @Override
    public boolean deleteById(Integer book_id) {
        String sql = "DELETE FROM book_authors WHERE book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, book_id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book_authors", e);
        }
    }

    @Override
    public BookAuthor update(BookAuthor entity) {
        String UPDATE = "UPDATE book_authors SET book_id = ?, author_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
           ps.setInt(1, entity.getBookId());
           ps.setInt(2, entity.getAuthorId());
           ps.setInt(3, entity.getId());
           ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book_author", e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM book_authors";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting book_authors", e);
        }
        return 0;
    }

    @Override
    public List<BookAuthor> retrieveByBookId(Integer bookId) {
        List<BookAuthor> list = new ArrayList<>();
        String sql = "SELECT * FROM book_authors WHERE book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book_authors by bookId", e);
        }
        return list;
    }

    @Override
    public List<BookAuthor> retrieveByAuthorId(Integer authorId) {
        List<BookAuthor> list = new ArrayList<>();
        String sql = "SELECT * FROM book_authors WHERE author_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, authorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book_authors by authorId", e);
        }
        return list;
    }

    @Override
    public Optional<BookAuthor> retrieveByBookIdAndAuthorId(Integer bookId, Integer authorId) {
        String sql = "SELECT * FROM book_authors WHERE book_id = ? AND author_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ps.setInt(2, authorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book_authors by bookId and authorId", e);
        }
        return Optional.empty();
    }

    private BookAuthor mapRow(ResultSet rs) throws SQLException {
        return BookAuthor.builder()
                .bookId(rs.getInt("book_id"))
                .authorId(rs.getInt("author_id"))
                .build();
    }

        public static void main(String[] args) {
        BookAuthorRepository bookAuthorRepository = new BookAuthorRepositoryImpl();
//        bookAuthorRepository.retrieveAll().forEach(
//                bookAuthor -> {
//                    System.out.println("Publisher ID: " + bookAuthor.getBookId());
//                    System.out.println("Publisher Name: " + bookAuthor.getAuthorId());
//
//                    System.out.println("-----------------------------");
//
//                }
//        );
            bookAuthorRepository.deleteById(5);
    }
}
