package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Review;
import uz.kruz.repository.ReviewRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReviewRepositoryImpl implements ReviewRepository {

    private final Connection connection;

    public ReviewRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Review create(Review entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Review> retrieveById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Review> retrieveAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean deleteById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Review update(Review entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Review> retrieveByUserId(Integer userId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Review> retrieveByBookId(Integer bookId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Review> retrieveByRatingGreaterThanEqual(Integer rating) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Review> retrieveByReviewedAtAfter(LocalDateTime date) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
