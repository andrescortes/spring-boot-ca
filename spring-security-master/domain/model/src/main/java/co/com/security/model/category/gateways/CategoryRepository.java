package co.com.security.model.category.gateways;

import co.com.security.model.category.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
//    Page<Category> findAll(Pageable pageable);
    List<Category> findAll();

    Optional<Category> findOneById(Long categoryId);

    Category createOne(Category saveCategory);

    Category updateOneById(Long categoryId, Category saveCategory);

    Optional<Category> disableOneById(Long categoryId);
}
