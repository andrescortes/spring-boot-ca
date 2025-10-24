package co.com.security.api.product;

import co.com.security.api.product.dto.ProductPageDto;
import co.com.security.model.product.Product;
import co.com.security.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RequestMapping(name = "products", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class ProductRest {

    private final ProductUseCase productUseCase;

    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<ProductPageDto> findAll(@PathVariable Long page, @PathVariable Long size) {
        if (Objects.isNull(page) || Objects.isNull(size)) {
            return ResponseEntity.badRequest().build();
        }
        List<Product> products = productUseCase.findAll();
        ProductPageDto pageDto = ProductPageDto.builder().page(page).size(size).products(products.subList(0,
                products.size() - 1)).total(products.size()).build();
        return ResponseEntity.ok(pageDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        if (Objects.isNull(productId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productUseCase.findOne(productId));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productUseCase.createCategory(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        if (Objects.isNull(productId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productUseCase.updateCategory(productId, product));
    }

    @PutMapping("/{productId}/disabled")
    public ResponseEntity<Product> disableProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productUseCase.disableOneById(productId));
    }
}
