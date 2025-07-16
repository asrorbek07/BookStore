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
    private final String INSERT = "INSERT INTO books (title, isbn, price, stock,published_year) VALUES (?, ?, ?, ?, ?)";
    private final String SELECTBYID = "SELECT * FROM books WHERE id = ?";
    private final String DELETEBYID = "DELETE FROM books WHERE id = ?";
    private final String SELECTALL = "SELECT * FROM books";
    private final String UPDATE = "UPDATE books SET title = ?, isbn = ?, price = ?, stock = ?,published_year = ? where id = ?";
    private final String COUNT = "SELECT COUNT(*) FROM books";
    private final String BYISBN = "SELECT * FROM books WHERE isbn = ?";
    private final String BYTITLE = "SELECT * FROM books WHERE title = ?";
    private final String BYCATEGORY = "SELECT * FROM books WHERE title = ?";
    private final String BYPUBLISHER = "SELECT * FROM books WHERE published_year = ?";
    private final String BYAUTHORID = "SELECT * FROM books WHERE published_year = ?";
    private final String BYSTOCKLESS = "SELECT * FROM books WHERE stock < ?";


    public BookRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Book create(Book entity) {

        try {
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
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

        try (PreparedStatement ps = connection.prepareStatement(SELECTBYID)) {
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

        try (Statement statement = (Statement) connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SELECTALL);
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

        try (PreparedStatement ps = connection.prepareStatement(DELETEBYID)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book", e);
        }
    }

    @Override
    public Book update(Book entity) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {

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

        try (Statement statement = (Statement) connection.createStatement()) {
            ResultSet rs = statement.executeQuery(COUNT);
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

        try (PreparedStatement ps = connection.prepareStatement(BYISBN)) {
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

        try (PreparedStatement ps = connection.prepareStatement(BYTITLE)) {
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

        try (PreparedStatement ps = connection.prepareStatement(BYCATEGORY)) {
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

        try (PreparedStatement ps = connection.prepareStatement(BYPUBLISHER)) {
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

        try (PreparedStatement ps = connection.prepareStatement(BYAUTHORID)) {
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

        try (PreparedStatement ps = connection.prepareStatement(BYSTOCKLESS)) {
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

        BookRepositoryImpl bookRepository = new BookRepositoryImpl();
//        Book book1 = bookRepository.create(book);
//        System.out.println(book1);
        System.out.println(bookRepository.count());
        List<Book> books = bookRepository.retrieveAll();
        System.out.println(books.toString());
        bookRepository.deleteById(5);

    }
}
