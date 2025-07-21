package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Review;
import uz.kruz.repository.ReviewRepository;
import uz.kruz.util.exceptions.DatabaseUnavailableException;
import uz.kruz.util.exceptions.EntityNotFoundException;
import uz.kruz.util.exceptions.RepositoryException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewRepositoryImpl implements ReviewRepository {

    private final Connection connection;
    private static final String INSERT = "insert into reviews (user_id, book_id, rating, comment) VALUES (?,?,?,?)";
    private static final String SELECT_BY_ID = "select * from reviews where id = ?";
    private static final String SELECT_ALL = "select * from reviews";
    private static final String DELETE = "delete from reviews where id = ?";
    private static final String UPDATE = "update reviews set user_id = ?, book_id = ?, rating = ? where id = ?";
    private static final String COUNT = "select count(*) from reviews";
    private static final String SELECT_BY_USER_ID = "select * from reviews where user_id = ?";
    private static final String SELECT_BY_BOOK_ID = "select * from reviews where book_id = ?";
    private static final String SELECT_BY_RATING = "select * from reviews where rating = ?";
    private static final String SELECT_BY_REVIEWED_AT = "select * from reviews where reviewed_at = ?";

    public ReviewRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new DatabaseUnavailableException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Review create(Review entity) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getUserId());
            ps.setInt(2, entity.getBookId());
            ps.setDouble(3, entity.getRating());
            ps.setString(4, entity.getComment());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return entity;
        } catch (SQLException e) {
            throw new RepositoryException("Error inserting review", e);
        }
    }

    @Override
    public Optional<Review> retrieveById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving review by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Review> retrieveAll() {
        List<Review> reviews = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SELECT_ALL);
            while (rs.next()) {
                reviews.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving all reviews", e);
        }
        return reviews;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            if (affected == 0) throw new EntityNotFoundException(String.format("Review with id %d not found", id));
            return true;
        } catch (SQLException e) {
            throw new RepositoryException("Error deleting review", e);
        }
    }

    @Override
    public Review update(Review entity) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setInt(1, entity.getUserId());
            ps.setInt(2, entity.getBookId());
            ps.setDouble(3, entity.getRating());
            int updated = ps.executeUpdate();
            if (updated == 0) throw new EntityNotFoundException(String.format("Review with id %d not found", entity.getId()));
            return entity;
        } catch (SQLException e) {
            throw new RepositoryException("Error updating review", e);
        }
    }

    @Override
    public long count() {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(COUNT);
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error counting reviews", e);
        }
        return 0;
    }

    @Override
    public boolean existsById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RepositoryException("Error checking existence by ID", e);
        }
    }

    @Override
    public List<Review> retrieveByUserId(Integer userId) {
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_USER_ID)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving reviews by userId", e);
        }
        return reviews;
    }

    @Override
    public List<Review> retrieveByBookId(Integer bookId) {
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_BOOK_ID)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving reviews by bookId", e);
        }
        return reviews;
    }

    @Override
    public List<Review> retrieveByRatingGreaterThanEqual(Integer rating) {
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_RATING)) {
            ps.setInt(1, rating);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving reviews by rating", e);
        }
        return reviews;
    }

    @Override
    public List<Review> retrieveByReviewedAtAfter(LocalDateTime date) {
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_REVIEWED_AT)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error retrieving reviews by reviewed_at", e);
        }
        return reviews;
    }

    private Review mapRow(ResultSet rs) throws SQLException {
        return Review.builder()
                .id(rs.getInt("id"))
                .userId(rs.getInt("user_id"))
                .bookId(rs.getInt("book_id"))
                .rating(rs.getInt("rating"))
                .comment(rs.getString("comment"))
                .build();
    }

    public static void main(String[] args) {
        ReviewRepositoryImpl reviewRepository = new ReviewRepositoryImpl();
        reviewRepository.retrieveByUserId(1);
        reviewRepository.retrieveByBookId(1);
        reviewRepository.retrieveByRatingGreaterThanEqual(1);
        reviewRepository.retrieveByReviewedAtAfter(LocalDateTime.now());
        reviewRepository.deleteById(1);
    }
}
