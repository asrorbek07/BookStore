package uz.kruz.service.impl;

import uz.kruz.dto.BaseDTO;
import uz.kruz.repository.ReviewRepository;
import uz.kruz.service.ReviewService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReviewServiceImpl<D extends BaseDTO> implements ReviewService<D> {
    
    private final ReviewRepository reviewRepository;
    
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    
    @Override
    public D register(D dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }
    
    @Override
    public Optional<D> findById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }
    
    @Override
    public List<D> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }
    
    @Override
    public boolean removeById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }
    
    @Override
    public D modify(D dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }
    
    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }
    
    @Override
    public List<D> findByUserId(Integer userId) {
        throw new UnsupportedOperationException("Method not implemented");
    }
    
    @Override
    public List<D> findByBookId(Integer bookId) {
        throw new UnsupportedOperationException("Method not implemented");
    }
    
    @Override
    public List<D> findByRatingGreaterThanEqual(Integer rating) {
        throw new UnsupportedOperationException("Method not implemented");
    }
    
    @Override
    public List<D> findByReviewedAtAfter(LocalDateTime date) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}