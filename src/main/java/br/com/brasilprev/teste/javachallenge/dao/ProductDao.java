package br.com.brasilprev.teste.javachallenge.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.brasilprev.teste.javachallenge.model.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {
}
