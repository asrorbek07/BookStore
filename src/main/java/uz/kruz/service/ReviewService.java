package uz.kruz.service;

import uz.kruz.domain.Review;
import uz.kruz.dto.ReviewDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewService extends Service<ReviewDTO, Review, Integer> {

    List<Review> findByUserId(Integer userId);

    List<Review> findByBookId(Integer bookId);

    List<Review> findByRatingGreaterThanEqual(Integer rating);

    List<Review> findByReviewedAtAfter(LocalDateTime date);
}
