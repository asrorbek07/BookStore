package uz.kruz.repository;

import uz.kruz.domain.Review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends Repository<Review, Integer> {

    Optional<Review> retrieveByUserId(Integer userId);

    List<Review> retrieveByBookId(Integer bookId);

    List<Review> retrieveByRatingGreaterThanEqual(Integer rating);

    List<Review> retrieveByReviewedAtAfter(LocalDateTime date);
}