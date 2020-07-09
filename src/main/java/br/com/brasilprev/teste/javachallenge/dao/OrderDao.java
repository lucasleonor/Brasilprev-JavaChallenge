package br.com.brasilprev.teste.javachallenge.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.com.brasilprev.teste.javachallenge.model.Order;

public interface OrderDao extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.customer.id = ?1 and o.id = ?2")
    Optional<Order> findById(Integer customerId, Integer orderId);
}
