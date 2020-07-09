package br.com.brasilprev.teste.javachallenge.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import br.com.brasilprev.teste.javachallenge.dao.OrderDao;
import br.com.brasilprev.teste.javachallenge.model.Customer;
import br.com.brasilprev.teste.javachallenge.model.Order;
import br.com.brasilprev.teste.javachallenge.model.OrderItem;

@Service
public class OrderService {

    private OrderDao dao;
    private CustomerService customerService;
    private OrderItemService orderItemService;

    @Autowired
    public OrderService(OrderDao dao, CustomerService customerService, OrderItemService orderItemService) {
        this.dao = dao;
        this.customerService = customerService;
        this.orderItemService = orderItemService;
    }

    public Page<Order> listOrders(Integer customerId, Pageable pageable) {
        customerService.findById(customerId);
        Order order = new Order();
        Customer customer = new Customer();
        customer.setId(customerId);
        order.setCustomer(customer);
        return dao.findAll(Example.of(order), pageable);
    }

    public Order findById(Integer customerId, Integer orderId) {
        return dao.findById(customerId, orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Order placeOrder(Integer customerId, Order orderParam) {
        Customer customer = customerService.findById(customerId);
        Set<OrderItem> items = orderParam.getItems();

        orderParam.setItems(Collections.emptySet());
        orderParam.setCustomer(customer);
        orderParam.setStatus("PLACED");

        Order order = dao.save(orderParam);

        items.forEach(orderItem -> orderItem.setOrder(order));
        items = new HashSet<>(orderItemService.saveItems(items));
        order.setItems(items);
        return order;
    }

    public void cancelOrder(Integer customerId, Integer orderId) {
        Order order = findById(customerId, orderId);
        order.setStatus("CANCELED");
        dao.save(order);
    }
}
