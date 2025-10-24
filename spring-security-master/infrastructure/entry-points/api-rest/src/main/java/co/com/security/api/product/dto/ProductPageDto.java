package co.com.security.api.product.dto;

import co.com.security.model.product.Product;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public class ProductPageDto {
    Long page;
    Long size;
    int total;
    List<Product> products;
}
