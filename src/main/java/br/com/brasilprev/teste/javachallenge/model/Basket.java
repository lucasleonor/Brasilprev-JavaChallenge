package br.com.brasilprev.teste.javachallenge.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "basket")
public class Basket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToMany
    @JoinColumn(name = "basket_id")
    private Set<BasketItem> products;

    public void addProduct(Product product) {
        BasketItem basketItem = findProduct(product);
        if (basketItem == null) {
            products.add(new BasketItem(product, 1));
        } else {
            basketItem.addProduct();
        }
    }

    public void updateProductAmount(Product product, Integer amount) {
        BasketItem basketItem = findProduct(product);
        if (basketItem == null) {
            products.add(new BasketItem(product, amount));
        } else {
            basketItem.setAmount(amount);
        }
    }

    public void removeProduct(Product product) {
        BasketItem basketItem = findProduct(product);
        if (basketItem != null) {
            products.remove(basketItem);
        }
    }

    public BasketItem findProduct(Product product) {
        return products.stream().filter(item -> item.getProduct().equals(product)).findFirst().orElse(null);
    }
}
