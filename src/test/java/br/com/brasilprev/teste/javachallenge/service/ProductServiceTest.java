package br.com.brasilprev.teste.javachallenge.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;
import br.com.brasilprev.teste.javachallenge.dao.ProductDao;
import br.com.brasilprev.teste.javachallenge.model.Product;
import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDao dao;
    private ProductService service;
    private int id;
    private Product product;

    @BeforeEach
    void setUp() {
        service = new ProductService(dao);
        id = new Random().nextInt();
        product = new Product();
        product.setName(RandomString.make());
    }

    @Test
    void listProducts() {
        int randomInt = Math.abs(new Random().nextInt());
        PageRequest pageRequest = PageRequest.of(randomInt, randomInt);
        PageImpl<Product> productsPage = new PageImpl<>(Collections.singletonList(product));

        when(dao.findAll(Example.of(product), pageRequest)).thenReturn(productsPage);
        assertThat(service.listProducts(product, pageRequest), is(productsPage));
        verify(dao, times(1)).findAll(Example.of(product), pageRequest);
    }

    @Test
    void findById() {
        when(dao.findById(id)).thenReturn(Optional.of(product));
        assertThat(service.findById(id), is(product));
        verify(dao, times(1)).findById(id);
    }

    @Test
    void findById_notInBase() {
        when(dao.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.findById(id));
    }

    @Test
    void registerProduct() {
        when(dao.save(new Product())).thenReturn(product);
        assertThat(service.registerProduct(new Product()), is(product));
        verify(dao, times(1)).save(new Product());
    }

    @Test
    void updateProduct() {
        Product productWithId = new Product();
        productWithId.setId(id);
        when(dao.findById(id)).thenReturn(Optional.of(product));
        when(dao.save(productWithId)).thenReturn(product);
        assertThat(service.updateProduct(id, new Product()), is(product));
        verify(dao, times(1)).save(productWithId);
    }
}