package br.com.brasilprev.teste.javachallenge.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import br.com.brasilprev.teste.javachallenge.model.Product;
import br.com.brasilprev.teste.javachallenge.service.ProductService;
import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService service;
    private ProductController controller;
    private int id;
    private Product product;

    @BeforeEach
    void setUp() {
        controller = new ProductController(service);
        id = new Random().nextInt();
        product = new Product();
        product.setName(RandomString.make());
    }

    @Test
    void listProducts() {
        int randomInt = Math.abs(new Random().nextInt());
        PageRequest pageRequest = PageRequest.of(randomInt, randomInt);
        PageImpl<Product> productsPage = new PageImpl<>(Collections.singletonList(product));

        when(service.listProducts(product, pageRequest)).thenReturn(productsPage);
        assertThat(controller.listProducts(product, pageRequest), is(productsPage));
        verify(service, times(1)).listProducts(product, pageRequest);
    }

    @Test
    void findById() {
        when(service.findById(id)).thenReturn(product);
        assertThat(controller.findById(id), is(product));
        verify(service, times(1)).findById(id);
    }

    @Test
    void registerProduct() {
        when(service.registerProduct(new Product())).thenReturn(product);
        assertThat(controller.registerProduct(new Product()), is(product));
        verify(service, times(1)).registerProduct(new Product());
    }

    @Test
    void updateProduct() {
        when(service.updateProduct(id, new Product())).thenReturn(product);
        assertThat(controller.updateProduct(id, new Product()), is(product));
        verify(service, times(1)).updateProduct(id, new Product());
    }
}