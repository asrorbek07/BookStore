package uz.kruz.service.impl;

import uz.kruz.domain.Review;
import uz.kruz.dto.ReviewDTO;
import uz.kruz.repository.ReviewRepository;
import uz.kruz.service.ReviewService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review register(ReviewDTO dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Review> findById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Review> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean removeById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Review modify(ReviewDTO dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Review> findByUserId(Integer userId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Review> findByBookId(Integer bookId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Review> findByRatingGreaterThanEqual(Integer rating) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Review> findByReviewedAtAfter(LocalDateTime date) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}