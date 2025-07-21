package uz.kruz.service.impl;

import uz.kruz.domain.Category;
import uz.kruz.dto.CategoryDTO;
import uz.kruz.repository.CategoryRepository;
import uz.kruz.service.CategoryService;
import uz.kruz.util.StringUtil;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category register(CategoryDTO dto) {
       if (StringUtil.isEmpty(dto.getName())){
           throw new IllegalArgumentException("name must not be empty");
       }
        Category category=Category.builder().name(dto.getName()).build();
       return categoryRepository.create(category);
    }


    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.retrieveById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        return categoryRepository.deleteById(id);
    }

    @Override
    public Category modify(CategoryDTO dto, Integer id) {
        Category category=categoryRepository.retrieveById(id).orElseThrow(() -> new RuntimeException("category not found"+id));
        if (dto.getName()!=null&&!StringUtil.isEmpty(dto.getName())){
            category.setName(dto.getName());
        }
        return categoryRepository.update(category);
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public Category findByName(String name) {
        if (StringUtil.isEmpty(name)) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        return categoryRepository.retrieveByName(name).orElseThrow();

    }

}
