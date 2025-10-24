package co.com.security.usecase.product;

import co.com.security.model.exceptions.ObjectNotFoundException;
import co.com.security.model.product.Product;
import co.com.security.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findOne(Long productId) {
        return productRepository.findOneById(productId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Product with id: %s not found", productId)));
    }

    public Product createCategory(Product product) {
        return productRepository.createOne(product);
    }

    public Product updateCategory(Long productId, Product product) {
        return productRepository.updateOneById(productId, product);
    }

    public Product disableOneById(Long productId) {
        return productRepository.disableOneById(productId);
    }
}
