package uz.kruz.service;

import uz.kruz.domain.Category;
import uz.kruz.dto.CategoryDTO;

import java.util.List;

public interface CategoryService extends Service<CategoryDTO, Category, Integer> {

    List<Category> findByName(String name);
}
