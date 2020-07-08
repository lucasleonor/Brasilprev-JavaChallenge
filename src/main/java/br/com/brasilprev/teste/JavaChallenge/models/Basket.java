package br.com.brasilprev.teste.JavaChallenge.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
public class Basket implements Serializable {
    private Client client;
    private Map<Product, Integer> products;

    public void addProduct(Product product) {
        products.compute(product, (k, v) -> v == null ? 1 : v+1);
    }

    public void updateProductAmount(Product product, Integer amount) {
        products.put(product, amount);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
