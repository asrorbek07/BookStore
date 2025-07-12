package uz.kruz.service;

import uz.kruz.dto.BaseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewService<D extends BaseDTO> extends Service<D, Integer> {

    List<D> findByUserId(Integer userId);

    List<D> findByBookId(Integer bookId);

    List<D> findByRatingGreaterThanEqual(Integer rating);

    List<D> findByReviewedAtAfter(LocalDateTime date);
}
