package uz.kruz.service;

import uz.kruz.domain.Category;
import uz.kruz.dto.CategoryDTO;

public interface CategoryService extends Service<CategoryDTO, Category, Integer> {

    Category findByName(String name);
}
