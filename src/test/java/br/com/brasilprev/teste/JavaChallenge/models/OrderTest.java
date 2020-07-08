package br.com.brasilprev.teste.JavaChallenge.models;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

class OrderTest {

    private Client client;
    private Order order;
    private Product product;
    private Map<Product, Integer> productMap;
    private int productAmount;

    @BeforeEach
    void setUp() {
        client = new Client(RandomString.make(), RandomString.make());
        order = new Order(client);
        product = new Product(RandomString.make(), new Random().nextFloat());

        productAmount = new Random().nextInt();
        productMap = new HashMap<>(Collections.singletonMap(product, productAmount));
        order.setProducts(productMap);
    }

    @Test
    void addProduct() {
        order.addProduct(product);
        assertThat(order.getProducts().get(product), is(productAmount + 1));

        order.setProducts(new HashMap<>());
        order.addProduct(product);
        assertThat(order.getProducts().get(product), is(1));
    }

    @Test
    void updateProductAmount() {
        int newProductAmount = new Random().nextInt();
        order.updateProductAmount(product, newProductAmount);
        assertThat(order.getProducts().get(product), is(newProductAmount));
    }

    @Test
    void removeProduct() {
        order.removeProduct(product);
        assertThat(order.getProducts().get(product), is(nullValue()));
    }
}