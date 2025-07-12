package uz.kruz.service;

import uz.kruz.dto.BaseDTO;
import uz.kruz.domain.Category;

import java.util.List;

public interface CategoryService<D extends BaseDTO> extends Service<D, Integer> {

    List<D> findByName(String name);
}
