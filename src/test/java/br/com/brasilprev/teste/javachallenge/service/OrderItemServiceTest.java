package br.com.brasilprev.teste.javachallenge.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.server.ResponseStatusException;
import br.com.brasilprev.teste.javachallenge.dao.OrderItemDao;
import br.com.brasilprev.teste.javachallenge.model.Order;
import br.com.brasilprev.teste.javachallenge.model.OrderItem;
import br.com.brasilprev.teste.javachallenge.model.Product;

@SpringBootTest
public class OrderItemServiceTest {

    @Autowired
    private OrderItemService service;
    private Order order;
    private Product product;
    private OrderItem orderItem;
    private Set<OrderItem> orderItems;

    @BeforeEach
    public void setUp() {
        order = new Order();
        order.setId(1);

        product = new Product();
        product.setId(1);

        orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setAmount(new Random().nextInt());

        orderItems = Collections.singleton(orderItem);
    }

    @Test
    public void saveItems() {
        service.saveItems(orderItems);
    }

    @Test
    public void saveItems_orderNotInDB() {
        order.setId(0);
        assertThrows(ResponseStatusException.class, () -> service.saveItems(orderItems));
    }

    @Test
    public void saveItems_productNotInDB() {
        product.setId(0);
        assertThrows(ResponseStatusException.class, () -> service.saveItems(orderItems));
    }

    @Test
    public void saveItems_JpaObjectRetrievalFailureException() {
        OrderItemDao dao = mock(OrderItemDao.class);
        when(dao.saveAll(orderItems)).thenThrow(new JpaObjectRetrievalFailureException(new EntityNotFoundException()));
        assertThrows(ResponseStatusException.class, () -> new OrderItemService(dao).saveItems(orderItems));
    }
}