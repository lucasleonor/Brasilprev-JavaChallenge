package br.com.brasilprev.teste.javachallenge.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

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
        Set<BasketItem> products = new HashSet<>(Collections.singleton(new BasketItem(product, productAmount)));
        basket.setProducts(products);
    }

    @Test
    void findProduct() {
        BasketItem basketItem = basket.findProduct(this.product);
        assertThat(basketItem.getProduct(), is(product));
    }

    @Test
    void addProduct() {
        basket.addProduct(product);
        assertThat(basket.findProduct(product).getAmount(), is(productAmount + 1));

        basket.setProducts(new HashSet<>());
        basket.addProduct(product);
        assertThat(basket.findProduct(product).getAmount(), is(1));
    }

    @Test
    void updateProductAmount() {
        int newProductAmount = new Random().nextInt();
        basket.updateProductAmount(product, newProductAmount);
        assertThat(basket.findProduct(product).getAmount(), is(newProductAmount));
    }

    @Test
    void removeProduct() {
        basket.removeProduct(product);
        assertThat(basket.findProduct(product), is(nullValue()));
    }
}