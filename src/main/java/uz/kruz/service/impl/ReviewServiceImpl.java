package uz.kruz.service.impl;

import uz.kruz.domain.Review;
import uz.kruz.dto.ReviewDTO;
import uz.kruz.repository.ReviewRepository;
import uz.kruz.service.ReviewService;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.EntityNotFoundException;
import uz.kruz.util.exceptions.ServiceException;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Optional;

public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review register(ReviewDTO dto) {
        if (dto == null) {
            throw new ServiceException("ReviewDTO must not be null");
        }
        Validator.validateInteger(dto.getUserId(),  "User ID");
        Validator.validateInteger(dto.getBookId(),  "Book ID");
        Validator.validateInteger(dto.getRating(),  "Rating value");
        Validator.validateString(dto.getComment(),  "Comment text");
        Review review=Review.builder().userId(dto.getUserId()).
                bookId(dto.getBookId()).
                rating(dto.getRating()).
                comment(dto.getComment()).build();
        return reviewRepository.create(review);
    }

    @Override
    public Optional<Review> findById(Integer id) {
        Validator.validateInteger(id, "Review ID");
        return reviewRepository.retrieveById(id);
    }

    @Override
    public List<Review> findAll() {

        return reviewRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        Validator.validateInteger(id, "Review ID");
        if (reviewRepository.retrieveById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format("Review with ID %d not found", id));
        }
        return reviewRepository.deleteById(id);
    }

    @Override
    public Review modify(ReviewDTO dto, Integer id) {
        Validator.validateInteger(id, " Review ID");

        Review review = reviewRepository.retrieveById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Review with ID %d not found", id)));

        boolean modified = false;
        Validator.validateInteger(review.getUserId(),  "User ID");
        Validator.validateInteger(review.getBookId(),  "Book ID");
        if (dto.getRating() != null) {
            Validator.validateInteger(dto.getRating(),  "Rating value");
            review.setRating(dto.getRating());
            modified = true;
        }
        if (dto.getComment() != null) {
            Validator.validateString(dto.getComment(), "Comment text");
            review.setComment(dto.getComment());
            modified = true;
        }
        if (!modified) {
            throw new ServiceException("No fields were provided");
        }
        return reviewRepository.update(review);

    }

    @Override
    public long count() {
        return reviewRepository.count();
    }

    @Override
    public boolean existsById(Integer integer) {
        return reviewRepository.existsById(integer);
    }

    @Override
    public List<Review> findByUserId(Integer userId) {
        Validator.validateInteger(userId, "User ID");
        return reviewRepository.retrieveByUserId(userId);
    }

    @Override
    public List<Review> findByBookId(Integer bookId) {
        Validator.validateInteger(bookId, "Book ID");
        return reviewRepository.retrieveByBookId(bookId);
    }

    @Override
    public List<Review> findByRatingGreaterThanEqual(Integer rating) {
        Validator.validateInteger(rating, "Rating value");
        return reviewRepository.retrieveByRatingGreaterThanEqual(rating);
    }

    @Override
    public List<Review> findByReviewedAtAfter(LocalDateTime date) {
        LocalDateTime now = LocalDateTime.now();
        if (date.isAfter(now)) {
            throw new ServiceException("ReviewedAt cannot be in the future");
        }
        return reviewRepository.retrieveByReviewedAtAfter(date);
    }
}