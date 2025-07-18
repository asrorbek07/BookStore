package uz.kruz.repository;

import uz.kruz.domain.Review;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends Repository<Review, Integer> {

    List<Review> retrieveByUserId(Integer userId);

    List<Review> retrieveByBookId(Integer bookId);

    List<Review> retrieveByRatingGreaterThanEqual(Integer rating);

    List<Review> retrieveByReviewedAtAfter(LocalDateTime date);
}