package uz.kruz.service.impl;

import uz.kruz.dto.BaseDTO;
import uz.kruz.repository.CategoryRepository;
import uz.kruz.service.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl<D extends BaseDTO> implements CategoryService<D> {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
    public List<D> findByName(String name) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
