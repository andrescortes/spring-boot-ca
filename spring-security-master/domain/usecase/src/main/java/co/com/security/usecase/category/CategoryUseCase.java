package co.com.security.usecase.category;

import co.com.security.model.category.Category;
import co.com.security.model.category.gateways.CategoryRepository;
import co.com.security.model.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryUseCase {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findOne(Long categoryId) {
        return categoryRepository.findOneById(categoryId)
                .orElseThrow(() -> new ObjectNotFoundException("Category not found"));
    }

    public Category createCategory(Category category) {
        return categoryRepository.createOne(category);
    }

    public Category updateCategory(Long categoryId, Category category) {
        return categoryRepository.updateOneById(categoryId, category);
    }

    public Category deleteCategory(Long categoryId) {
        return categoryRepository.disableOneById(categoryId);
    }
}
