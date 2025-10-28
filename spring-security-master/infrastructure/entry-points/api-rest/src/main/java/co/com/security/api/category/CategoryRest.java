package co.com.security.api.category;

import co.com.security.api.category.dto.CategoryDto;
import co.com.security.api.category.dto.CategoryPageDto;
import co.com.security.model.category.Category;
import co.com.security.usecase.category.CategoryUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryRest {

    private final CategoryUseCase useCase;

    @GetMapping("page/{page}/offset/{pageSize}")
    public ResponseEntity<CategoryPageDto> getCategories(@PathVariable Long page, @PathVariable Long pageSize) {
        if (page < 1 || page > pageSize) {
            throw new IllegalArgumentException("Invalid page");
        }
        List<Category> categories = useCase.findAll();
        if (categories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CategoryPageDto dto = CategoryPageDto.builder()
                .total(categories.size())
                .size(pageSize)
                .page(page)
                .categories(categories.stream().limit(pageSize).toList())
                .build();
        if (page != 1) {
            dto.toBuilder()
                    .categories(categories.stream().skip(pageSize * page).limit(pageSize).toList())
                    .build();
        }

        return ResponseEntity.ok(dto);
    }

    @GetMapping("by-id/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return useCase.findOne(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid CategoryDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(useCase.createCategory(Category.builder()
                        .name(dto.getName())
                        .build())
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDto dto) {
        return ResponseEntity.ok(useCase.updateCategory(id, Category.builder()
                .name(dto.getName())
                .build()));
    }

    @DeleteMapping("/{id}/disabled")
    public ResponseEntity<Category> disableCategory(@PathVariable Long id) {
        return useCase.deleteCategory(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
