package co.com.security.jpa.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ICategoryJPARepository extends CrudRepository<CategoryData/* change for adapter model */, Long>, QueryByExampleExecutor<CategoryData/* change for adapter model */> {
}
