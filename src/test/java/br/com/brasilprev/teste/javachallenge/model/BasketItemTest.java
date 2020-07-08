package br.com.brasilprev.teste.javachallenge.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class BasketItemTest {

    private BasketItem basketItem;

    @BeforeEach
    void setUp() {
        basketItem = new BasketItem();
    }

    @Test
    void addProduct() {
        basketItem.addProduct();
        assertThat(basketItem.getAmount(), is(1));

        int amount = new Random().nextInt();
        basketItem.setAmount(amount);
        basketItem.addProduct();
        assertThat(basketItem.getAmount(), is(amount + 1));
    }
}