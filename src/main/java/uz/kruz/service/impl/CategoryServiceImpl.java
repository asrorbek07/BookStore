package uz.kruz.service.impl;

import uz.kruz.domain.Category;
import uz.kruz.dto.CategoryDTO;
import uz.kruz.repository.CategoryRepository;
import uz.kruz.service.CategoryService;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.DuplicateEntityException;
import uz.kruz.util.exceptions.EntityNotFoundException;
import uz.kruz.util.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category register(CategoryDTO dto) {
        if (dto == null) {
            throw new ServiceException("CategoryDTO must not be null");
        }
        Validator.validateString(dto.getName(), "Name");
        categoryRepository.retrieveByName(dto.getName()).ifPresent(category -> {
            throw new DuplicateEntityException(String.format("Category with name '%s' already exists", dto.getName()));
        });
        Category category = Category.builder().name(dto.getName()).build();
        return categoryRepository.create(category);
    }


    @Override
    public Optional<Category> findById(Integer id) {
        Validator.validateId(id);
        return categoryRepository.retrieveById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        Validator.validateId(id);
        if (categoryRepository.retrieveById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format("Category with ID '%d' not found", id));
        }
        return categoryRepository.deleteById(id);
    }

    @Override
    public Category modify(CategoryDTO dto, Integer id) {
        Validator.validateId(id);

        Category category = categoryRepository.retrieveById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with ID '%d' not found", id)));
        boolean modified = false;


        if (dto.getName() != null) {
            Validator.validateString(dto.getName(), "name");
            category.setName(dto.getName());
            modified = true;
        }
        if (!modified) {
            throw new ServiceException("No fields provided for update");
        }
        return categoryRepository.update(category);
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Category findByName(String name) {
        Validator.validateString(name, "Name");

        return categoryRepository.retrieveByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with name '%s' not found", name)));

    }

}
