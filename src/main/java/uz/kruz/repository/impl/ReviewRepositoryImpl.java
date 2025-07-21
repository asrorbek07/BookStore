package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Review;
import uz.kruz.domain.User;
import uz.kruz.domain.vo.UserRole;
import uz.kruz.repository.ReviewRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewRepositoryImpl implements ReviewRepository {

    private final Connection connection;
    private final String SELECT_REVIEW_BYID = "select * from reviews where id = ?";
    private final String SELECT_REVIEW_ALL = "select * from reviews";
    private final String DELETE_REVIEW = "delete from reviews where id = ?";
    private final String UPDATE = "update reviews set user_id = ?, book_id = ?, rating = ? where id = ?";
    private final String COUNT_REVIEW = "select count(*) from reviews";
    private final String SELECT_REVIEW_USER_ID = "select * from reviews where user_id = ?";
    private final String SELECT_REVIEW_BOOKID = "select * from reviews where book_id = ?";
    private final String BY_RATING_GREATER = "select * from reviews where rating = ?";
    private final String BYREVIEWED_AT = "select * from reviews where reviewed_at = ?";








    public ReviewRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Review create(Review entity) {
        final String INSERT = "insert into reviews (user_id, book_id, rating, comment) VALUES (?,?,?,?)";
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Review> retrieveById(Integer id) {

        try (PreparedStatement ps = connection.prepareStatement(SELECT_REVIEW_BYID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Review> retrieveAll() {
        List<Review> reviews = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SELECT_REVIEW_ALL);
            while (rs.next()) {
                reviews.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_REVIEW)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Review update(Review entity) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setInt(1, entity.getUserId());
            ps.setInt(2, entity.getBookId());
            ps.setDouble(3, entity.getRating());
            ps.setInt(4, entity.getId());
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public long count() {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(COUNT_REVIEW);
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Review> retrieveByUserId(Integer userId) {
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_REVIEW_USER_ID)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(mapRow(rs));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    @Override
    public List<Review> retrieveByBookId(Integer bookId) {
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_REVIEW_BOOKID)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    @Override
    public List<Review> retrieveByRatingGreaterThanEqual(Integer rating) {
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(BY_RATING_GREATER)) {
            ps.setInt(1, rating);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(mapRow(rs));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    @Override
    public List<Review> retrieveByReviewedAtAfter(LocalDateTime date) {
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(BYREVIEWED_AT)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
//        reviewRepository.retrieveAll();
       reviewRepository.retrieveByUserId(1);
      reviewRepository.retrieveByBookId(1);
      reviewRepository.retrieveByRatingGreaterThanEqual(1);
      reviewRepository.retrieveByReviewedAtAfter(LocalDateTime.now());
      reviewRepository.deleteById(1);

    }
}
