package br.com.brasilprev.teste.javachallenge.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.brasilprev.teste.javachallenge.model.OrderItem;

public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {

}
