package uz.kruz.repository;

import uz.kruz.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends Repository<Category, Integer> {

    Optional<Category> retrieveByName(String name);
}
