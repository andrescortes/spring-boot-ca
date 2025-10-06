package co.com.security.jpa.product;

import co.com.security.jpa.helper.AdapterOperations;
import co.com.security.model.exceptions.ObjectNotFoundException;
import co.com.security.model.product.Product;
import co.com.security.model.product.gateways.ProductRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductJPARepositoryAdapter extends AdapterOperations<
        Product/* change for domain model */,
        ProductData/* change for adapter model */,
        Long,
        IProductJPARepository
        >
        // implements ModelRepository from domain
        implements ProductRepository {

    public ProductJPARepositoryAdapter(IProductJPARepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Product.class/* change for domain model */));
    }

    @Override
    public Optional<Product> findOneById(Long productId) {
        return repository.findById(productId)
                .map(super::toEntity);
    }

    @Override
    public Product createOne(Product saveProduct) {
        ProductData data = super.toData(saveProduct);
        ProductData save = repository.save(data);
        return super.toEntity(save);
    }

    @Override
    public Product updateOneById(Long productId, Product saveProduct) {
        return repository.findById(productId)
                .map(product -> {
                    Product entity = super.toEntity(product);
                    entity.setName(saveProduct.getName());
                    entity.setCategory(saveProduct.getCategory());
                    entity.setPrice(saveProduct.getPrice());
                    entity.setStatus(saveProduct.getStatus());
                    ProductData save = repository.save(super.toData(entity));
                    return super.toEntity(save);
                })
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Product: %s not found", productId)));
    }

    @Override
    public Product disableOneById(Long productId) {
        return repository.findById(productId)
                .map(productData -> {
                    repository.delete(productData);
                    return super.toEntity(productData);
                })
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Product: %s not found", productId)));
    }
}
