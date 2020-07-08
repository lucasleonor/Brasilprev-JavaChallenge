package br.com.brasilprev.teste.javachallenge.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "basket_item")
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "amount")
    private Integer amount;

    public BasketItem(Product product, Integer amount) {
        this.product = product;
        this.amount = amount;
    }

    public void addProduct() {
        if (amount == null){
            amount = 0;
        }
        amount++;
    }
}
