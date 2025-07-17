package uz.kruz.service.impl;

import uz.kruz.domain.Category;
import uz.kruz.dto.CategoryDTO;
import uz.kruz.repository.CategoryRepository;
import uz.kruz.service.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category register(CategoryDTO dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Category> findById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Category> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean removeById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Category modify(CategoryDTO dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Category> findByName(String name) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
