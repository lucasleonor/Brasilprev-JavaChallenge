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

class BasketTest {

    private Basket basket;
    private Product product;
    private int productAmount;

    @BeforeEach
    void setUp() {
        basket = new Basket();
        product = new Product();

        productAmount = new Random().nextInt();
        Map<Product, Integer> productMap = new HashMap<>(Collections.singletonMap(product, productAmount));
        basket.setProducts(productMap);
    }

    @Test
    void addProduct() {
        basket.addProduct(product);
        assertThat(basket.getProducts().get(product), is(productAmount + 1));

        basket.setProducts(new HashMap<>());
        basket.addProduct(product);
        assertThat(basket.getProducts().get(product), is(1));
    }

    @Test
    void updateProductAmount() {
        int newProductAmount = new Random().nextInt();
        basket.updateProductAmount(product, newProductAmount);
        assertThat(basket.getProducts().get(product), is(newProductAmount));
    }

    @Test
    void removeProduct() {
        basket.removeProduct(product);
        assertThat(basket.getProducts().get(product), is(nullValue()));
    }
}