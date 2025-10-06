package co.com.security.jpa.product;

import co.com.security.jpa.category.CategoryData;
import co.com.security.model.category.Category;
import co.com.security.model.exceptions.ObjectNotFoundException;
import co.com.security.model.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductJPARepositoryAdapterTest {

    Product entity;
    ProductData data;
    @Mock
    private IProductJPARepository repository;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
    private ProductJPARepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        Category category = Category.builder()
                .id(1L)
                .name("Electronics")
                .status(Category.CategoryStatus.DISABLED)
                .build();
        entity = Product.builder()
                .id(1L)
                .name("TV")
                .price(new BigDecimal("1500.45"))
                .status(Product.ProductStatus.DISABLED)
                .category(category)
                .build();
        CategoryData categoryData = new CategoryData(1L, "Electronics", CategoryData.CategoryStatus.DISABLED);
        data = new ProductData(1L, "TV", new BigDecimal("1500.45"), ProductData.ProductStatus.DISABLED, categoryData);
    }

    @Test
    void findOneById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(data));
        Mockito.when(mapper.map(any(ProductData.class), any())).thenReturn(entity);

        Optional<Product> oneById = adapter.findOneById(1L);

        Assertions.assertTrue(oneById.isPresent());
        Product prodRetrieved = oneById.get();
        Assertions.assertEquals(entity.getName(), prodRetrieved.getName());
        Assertions.assertEquals(entity.getPrice(), prodRetrieved.getPrice());
    }

    @Test
    void createOne() {
        Mockito.when(mapper.map(any(Product.class), any())).thenReturn(data);
        Mockito.when(repository.save(any(ProductData.class))).thenReturn(data);
        Mockito.when(mapper.map(any(ProductData.class), any())).thenReturn(entity);

        Product one = adapter.createOne(entity);

        Assertions.assertNotNull(one);
        Assertions.assertEquals(entity.getName(), one.getName());
        Assertions.assertEquals(entity.getPrice(), one.getPrice());
    }

    @Test
    void updateOneById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(data));
        Mockito.when(mapper.map(any(Product.class), any())).thenReturn(data);
        Mockito.when(repository.save(any(ProductData.class))).thenReturn(data);
        Mockito.when(mapper.map(any(ProductData.class), any())).thenReturn(entity);

        Product one = adapter.updateOneById(1L, entity);

        Assertions.assertNotNull(one);
        Assertions.assertEquals(entity.getName(), one.getName());
        Assertions.assertEquals(entity.getPrice(), one.getPrice());
    }

    @Test
    void updateOneByIdNotFound() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        ObjectNotFoundException exception = Assertions.assertThrows(ObjectNotFoundException.class, () -> adapter.updateOneById(1L, entity));

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Product: 1 not found", exception.getMessage());
    }

    @Test
    void disableOneById() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(data));
        Mockito.doNothing().when(repository).delete(any(ProductData.class));
        Mockito.when(mapper.map(any(ProductData.class), any())).thenReturn(entity);

        Product product = adapter.disableOneById(1L);

        Assertions.assertNotNull(product);
        Assertions.assertEquals(entity.getName(), product.getName());
        Assertions.assertEquals(entity.getPrice(), product.getPrice());
    }

    @Test
    void disableOneByIdNotFound() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        ObjectNotFoundException exception = Assertions.assertThrows(ObjectNotFoundException.class, () -> adapter.disableOneById(1L));

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Product: 1 not found", exception.getMessage());

    }
}