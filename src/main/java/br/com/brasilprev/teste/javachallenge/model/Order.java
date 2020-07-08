package br.com.brasilprev.teste.javachallenge.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name="order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;
    @Column(name = "delivery_address")
    private String deliveryAddress;
}
