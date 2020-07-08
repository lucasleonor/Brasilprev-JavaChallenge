package br.com.brasilprev.teste.javachallenge.dao;

import br.com.brasilprev.teste.javachallenge.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {
}
