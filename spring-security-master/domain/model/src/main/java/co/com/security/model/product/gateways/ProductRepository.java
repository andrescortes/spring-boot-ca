package co.com.security.model.product.gateways;

import co.com.security.model.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
//    Page<Product> findAll(Pageable pageable);
    List<Product> findAll();

    Optional<Product> findOneById(Long productId);

    Product createOne(Product saveProduct);

    Product updateOneById(Long productId, Product saveProduct);

    Product disableOneById(Long productId);
}
