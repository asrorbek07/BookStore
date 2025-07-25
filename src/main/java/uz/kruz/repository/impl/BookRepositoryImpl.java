package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Book;
import uz.kruz.domain.BookAuthor;
import uz.kruz.repository.BookRepository;
import uz.kruz.util.exceptions.DatabaseUnavailableException;
import uz.kruz.util.exceptions.RowNotFoundException;
import uz.kruz.util.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {

    private final Connection connection;

    private static final String INSERT = "INSERT INTO books (title, isbn, price, stock, published_year,category_id,publisher_id) VALUES (?, ?, ?, ?, ?,?,?)";
    private static final String INSERT_INTO_book_authors = "INSERT INTO book_authors (author_id, book_id) VALUES (?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM books WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM books";
    private static final String UPDATE = "UPDATE books SET title = ?, isbn = ?, price = ?, stock = ?, published_year = ? WHERE id = ?";
    private static final String COUNT = "SELECT COUNT(*) FROM books";
    private static final String BY_ISBN = "SELECT * FROM books WHERE isbn = ?";
    private static final String BY_TITLE = "SELECT * FROM books WHERE title = ?";
    private static final String BY_CATEGORY = "SELECT * FROM books WHERE category_id = ?";
    private static final String BY_PUBLISHER = "SELECT * FROM books WHERE publisher_id = ?";
    private static final String BY_AUTHOR_ID = "SELECT b.* FROM books b JOIN book_authors ba ON b.id = ba.book_id WHERE ba.author_id = ?";
    private static final String BY_STOCK_LESS = "SELECT * FROM books WHERE stock < ?";
    private static final String EXIST_BY_ISBN = "select 1 from books where isbn = ?";

    public BookRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new DatabaseUnavailableException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Book create(Book entity) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getIsbn());
            ps.setBigDecimal(3, entity.getPrice());
            ps.setInt(4, entity.getStock());
            ps.setInt(5, entity.getPublishedYear());
            ps.setInt(6, entity.getCategory().getId());
            ps.setInt(7, entity.getPublisher().getId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return entity;
        } catch (SQLException e) {
            throw new RepositoryException("Error creating book", e);
        }
    }

//    public BookAuthor createBookAuthor(BookAuthor entity) {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_book_authors, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setString(1, entity.getBook().getTitle());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public Optional<Book> retrieveById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving book by id", e);
        }
    }

    @Override
    public List<Book> retrieveAll() {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving all books", e);
        }
        return books;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException("Error deleting book", e);
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

            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new RowNotFoundException("No book found for update with ID: " + entity.getId());
            }
            return entity;
        } catch (SQLException e) {
            throw new RepositoryException("Error updating book", e);
        }
    }

    @Override
    public long count() {
        try (PreparedStatement ps = connection.prepareStatement(COUNT)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RepositoryException("Error counting books", e);
        }
    }

    @Override
    public Optional<Book> retrieveByIsbn(String isbn) {
        try (PreparedStatement ps = connection.prepareStatement(BY_ISBN)) {
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving book by ISBN", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> retrieveByTitle(String title) {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(BY_TITLE)) {
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving book by title", e);
        }
        return books;
    }

    @Override
    public List<Book> retrieveByCategoryId(Integer categoryId) {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(BY_CATEGORY)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving books by category ID", e);
        }
        return books;
    }

    @Override
    public List<Book> retrieveByPublisherId(Integer publisherId) {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(BY_PUBLISHER)) {
            ps.setInt(1, publisherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving books by publisher ID", e);
        }
        return books;
    }

    @Override
    public List<Book> retrieveByAuthorId(Integer authorId) {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(BY_AUTHOR_ID)) {
            ps.setInt(1, authorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving books by author ID", e);
        }
        return books;
    }

    @Override
    public List<Book> retrieveByStockLessThan(Integer amount) {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(BY_STOCK_LESS)) {
            ps.setInt(1, amount);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving books with stock less than " + amount, e);
        }
        return books;
    }

    @Override
    public Boolean existsByIsbn(String isbn) {

        try (PreparedStatement ps = connection.prepareStatement(EXIST_BY_ISBN)) {
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RepositoryException("Error checking existence of book by ID", e);
        }
    }

    private Book mapRow(ResultSet rs) throws SQLException {
        return Book.builder()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .isbn(rs.getString("isbn"))
                .price(rs.getBigDecimal("price"))
                .stock(rs.getInt("stock"))
                .publishedYear(rs.getInt("published_year"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }
}
