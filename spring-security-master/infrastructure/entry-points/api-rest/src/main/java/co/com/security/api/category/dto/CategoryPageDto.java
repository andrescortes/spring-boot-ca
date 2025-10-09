package co.com.security.api.category.dto;

import co.com.security.model.category.Category;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public class CategoryPageDto {
    Long page;
    Long size;
    int total;
    List<Category> categories;
}
