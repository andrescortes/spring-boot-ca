package co.com.security.usecase.category;

import co.com.security.model.category.Category;
import co.com.security.model.category.gateways.CategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoryUseCase {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findOne(Long categoryId) {
        return categoryRepository.findOneById(categoryId);
    }

    public Category createCategory(Category category) {
        return categoryRepository.createOne(category);
    }

    public Category updateCategory(Long categoryId, Category category) {
        return categoryRepository.updateOneById(categoryId, category);
    }

    public Optional<Category> deleteCategory(Long categoryId) {
        return categoryRepository.disableOneById(categoryId);
    }
}
