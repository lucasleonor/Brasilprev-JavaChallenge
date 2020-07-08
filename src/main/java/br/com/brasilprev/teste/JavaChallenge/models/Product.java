package br.com.brasilprev.teste.JavaChallenge.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class Product implements Serializable {

    private String name;
    private Float price;

}
