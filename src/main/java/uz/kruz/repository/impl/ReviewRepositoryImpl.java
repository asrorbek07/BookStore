package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Review;
import uz.kruz.repository.ReviewRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewRepositoryImpl implements ReviewRepository {
    private final String CREATE = "INSERT INTO reviews (user_id, book_id, rating, comment) values (?, ?, ?, ?)";
    private final String SELECT = "SELECT * FROM reviews WHERE user_id = ?";
    private final String SELECT_ALL = "SELECT * FROM reviews";
    private final String DELETE = "DELETE FROM reviews WHERE id = ?";
    private final String UPDATE = "UPDATE reviews SET user_id = ?, book_id = ?, rating = ?, comment = ? WHERE id = ?";
    private final String COUNT = "SELECT COUNT(*) FROM reviews";
    private final String SELECT_BY_USER_ID = "SELECT * FROM reviews WHERE user_id = ?";
    private final String SELECT_ALL_BY_BOOK_ID = "SELECT * FROM reviews WHERE book_id LIKE ?";
    private final String SELECT_ALL_BY_rating = "SELECT * FROM reviews WHERE rating >= ?";
    private final Connection connection;

    public ReviewRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Review create(Review entity) { //ok
        try (PreparedStatement ps = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getUserId());
            ps.setInt(2, entity.getBookId());
            ps.setInt(3, entity.getRating());
            ps.setString(4, entity.getComment());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return entity;
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented");
        }
    }

    @Override
    public Optional<Review> retrieveById(Integer id) {     //oke
        try (PreparedStatement ps = connection.prepareStatement(SELECT)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving Review by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Review> retrieveAll() {
        List<Review> reviews = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                reviews.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all Review", e);
        }
        return reviews;
    }


    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Review", e);
        }
    }

    @Override
    public Review update(Review entity) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setInt(1, entity.getUserId());
            ps.setInt(2, entity.getBookId());
            ps.setInt(3, entity.getRating());
            ps.setString(4, entity.getComment());
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Review", e);
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
            throw new RuntimeException("Error counting Review", e);
        }
        return 0;
    }

    @Override
    public Optional<Review> retrieveByUserId(Integer userId) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_USER_ID)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving Review by email", e);
        }
        return Optional.empty();
    }


    @Override
    public List<Review> retrieveByBookId(Integer bookId) {
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL_BY_BOOK_ID)) {
            ps.setString(1, "%" + ps + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving Review by name", e);
        }
        return reviews;
    }


    @Override
    public List<Review> retrieveByRatingGreaterThanEqual(Integer rating) {
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL_BY_rating)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving Review by role", e);
        }
        return reviews;
    }


    @Override
    public List<Review> retrieveByReviewedAtAfter(LocalDateTime date) {
        List<Review> reviews = new ArrayList<>();
        String SELECT_BY_REVIEWED_AT_AFTER = "SELECT * FROM reviews WHERE reviewed_at > ?";
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_REVIEWED_AT_AFTER)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving reviews after date", e);
        }
        return reviews;
    }



    private Review mapRow(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setId(rs.getInt("id"));
        review.setUserId(rs.getInt("user_id"));
        review.setBookId(rs.getInt("book_id"));
        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));
        return review;
    }

    public static void main(String[] args) {
        ReviewRepositoryImpl repo = new ReviewRepositoryImpl();
//        Review review = new Review();
//        review.setUserId(4);
//        review.setBookId(7);
//        review.setRating(3)//    review.setComment("comment");

//        System.out.println(repo.deleteById());
    }
}
