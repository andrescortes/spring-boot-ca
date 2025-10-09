package co.com.security.api.category.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryDto {

    @NotEmpty
    private String name;
}
