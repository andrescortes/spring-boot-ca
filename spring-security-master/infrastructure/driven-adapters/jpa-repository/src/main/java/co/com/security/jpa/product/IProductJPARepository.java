package co.com.security.jpa.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface IProductJPARepository extends CrudRepository<ProductData/* change for adapter model */, Long>, QueryByExampleExecutor<ProductData/* change for adapter model */> {
}
