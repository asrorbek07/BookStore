package uz.kruz.repository.impl;

import org.mariadb.jdbc.Statement;
import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Book;
import uz.kruz.domain.User;
import uz.kruz.repository.BookRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {

    private final Connection connection;

    public BookRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Book create(Book entity) {

        String sql = "INSERT INTO books (title, isbn, price, stock,published_year) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getIsbn());
            ps.setBigDecimal(3, entity.getPrice());
            ps.setInt(4, entity.getStock());
            ps.setInt(5, entity.getPublishedYear());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Book> retrieveById(Integer id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
//                ------------------------
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> retrieveAll() {

        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Statement statement = (Statement) connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                books.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all books", e);
        }
        return books;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book", e);
        }
    }

    @Override
    public Book update(Book entity) {
        String sql = "UPDATE books SET title = ?, isbn = ?, price = ?, stock = ?,published_year = ? where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getIsbn());
            ps.setBigDecimal(3, entity.getPrice());
            ps.setInt(4, entity.getStock());
            ps.setInt(5, entity.getPublishedYear());
            ps.setInt(6, entity.getId());
            if (ps.executeUpdate() > 0)
                return entity;
            else
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM books";
        try (Statement statement = (Statement) connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error counting books", e);
        }
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Book> retrieveByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(isbn));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book by isbn", e);
        }
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Book> retrieveByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                books.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book by title", e);
        }
        return books;

    }

    @Override
    public List<Book> retrieveByCategoryId(Integer categoryId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE category_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book by category by id", e);
        }
        return books;
    }

    @Override
    public List<Book> retrieveByPublisherId(Integer publisherId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE published_year = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, publisherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book by publisher id", e);
        }
        return books;
    }

    @Override
    public List<Book> retrieveByAuthorId(Integer authorId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books join book_authors ba on books.id = ba.book_id where ba.author_id = ?" ;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, authorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book by author id", e);
        }
        return books;
    }

    @Override
    public List<Book> retrieveByStockLessThan(Integer amount) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE stock < ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, amount);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book by Stock", e);
        }
        return books;
    }

    private Book mapRow(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setIsbn(rs.getString("isbn"));
        book.setPrice(rs.getBigDecimal("price"));
        book.setStock(rs.getInt("stock"));
        book.setPublishedYear(rs.getInt("published_year"));
        return book;
    }

    public static void main(String[] args) {
        Book book = new Book();

        book.setTitle("Book 1");
        book.setIsbn("ISBN 1");
        book.setPrice(new BigDecimal("4512.45"));
        book.setStock(1);
        book.setPublishedYear(1948);

        BookRepositoryImpl bookRepository =  new BookRepositoryImpl();
        Book book1 = bookRepository.create(book);
        System.out.println(book1);


    }
}
