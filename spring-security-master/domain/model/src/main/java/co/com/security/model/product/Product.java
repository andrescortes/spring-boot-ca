package co.com.security.model.product;

import co.com.security.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {
    private Long id;

    private String name;

    private BigDecimal price;

    private ProductStatus status;

    private Category category;

    public enum ProductStatus {
        ENABLED, DISABLED;
    }
}
