package br.com.brasilprev.teste.javachallenge.dao;

import br.com.brasilprev.teste.javachallenge.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketDao extends JpaRepository<Basket, Integer> {
}
