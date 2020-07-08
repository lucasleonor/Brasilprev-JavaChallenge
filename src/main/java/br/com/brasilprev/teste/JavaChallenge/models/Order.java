package br.com.brasilprev.teste.JavaChallenge.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class Order implements Serializable {
    private Basket basket;
    private String deliveryAddress;
}
