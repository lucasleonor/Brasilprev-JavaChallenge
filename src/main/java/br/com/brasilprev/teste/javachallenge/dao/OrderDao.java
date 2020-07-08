package br.com.brasilprev.teste.javachallenge.dao;

import br.com.brasilprev.teste.javachallenge.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Integer> {
}
