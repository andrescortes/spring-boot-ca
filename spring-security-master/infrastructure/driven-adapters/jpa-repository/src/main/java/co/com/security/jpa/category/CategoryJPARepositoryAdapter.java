package co.com.security.jpa.category;

import co.com.security.jpa.helper.AdapterOperations;
import co.com.security.model.category.Category;
import co.com.security.model.category.gateways.CategoryRepository;
import co.com.security.model.exceptions.ObjectNotFoundException;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CategoryJPARepositoryAdapter extends AdapterOperations<Category/* change for domain model */, CategoryData/* change for adapter model */, Long, ICategoryJPARepository>
// implements ModelRepository from domain
        implements CategoryRepository {

    public CategoryJPARepositoryAdapter(ICategoryJPARepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Category.class/* change for domain model */));
    }

    @Override
    public Optional<Category> findOneById(Long categoryId) {
        return repository.findById(categoryId)
                .map(super::toEntity);
    }

    @Override
    public Category createOne(Category saveCategory) {
        CategoryData data = super.toData(saveCategory);
        CategoryData save = repository.save(data);
        return super.toEntity(save);

    }

    @Override
    public Category updateOneById(Long categoryId, Category saveCategory) {
        return findOneById(categoryId)
                .map(category -> {
                    CategoryData categoryData = super.toData(saveCategory);
                    CategoryData save = repository.save(categoryData);
                    return super.toEntity(save);
                })
                .orElseThrow(() -> new ObjectNotFoundException("Category not found"));
    }

    @Override
    public Category disableOneById(Long categoryId) {
        return findOneById(categoryId)
                .orElseThrow(() -> new ObjectNotFoundException("Category not found"));
    }
}
