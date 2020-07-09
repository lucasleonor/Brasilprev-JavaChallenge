package br.com.brasilprev.teste.javachallenge.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;
import br.com.brasilprev.teste.javachallenge.dao.OrderDao;
import br.com.brasilprev.teste.javachallenge.model.Customer;
import br.com.brasilprev.teste.javachallenge.model.Order;
import br.com.brasilprev.teste.javachallenge.model.OrderItem;
import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CustomerService customerService;
    @Mock
    private OrderItemService orderItemService;
    @Mock
    private OrderDao dao;
    private OrderService service;
    private Order order;
    private int id;
    private int customerId;

    @BeforeEach
    void setUp() {
        service = new OrderService(dao, customerService, orderItemService);
        customerId = new Random().nextInt();
        id = new Random().nextInt();
        order = new Order();
        order.setStatus(RandomString.make());
    }

    @Test
    void listOrders() {
        int randomInt = Math.abs(new Random().nextInt());
        PageRequest pageRequest = PageRequest.of(randomInt, randomInt);
        PageImpl<Order> ordersPage = new PageImpl<>(Collections.singletonList(this.order));

        Order order = new Order();
        Customer customer = new Customer();
        customer.setId(customerId);
        order.setCustomer(customer);
        when(dao.findAll(Example.of(order), pageRequest)).thenReturn(ordersPage);
        assertThat(service.listOrders(customerId, pageRequest), is(ordersPage));
        verify(dao, times(1)).findAll(Example.of(order), pageRequest);
    }

    @Test
    void findById() {
        when(dao.findById(customerId, id)).thenReturn(Optional.of(order));
        assertThat(service.findById(customerId, id), is(order));
        verify(dao, times(1)).findById(customerId, id);
    }

    @Test
    void findById_notInBase() {
        when(dao.findById(customerId, id)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.findById(customerId, id));
    }

    @Test
    void placeOrder() {
        when(customerService.findById(customerId)).thenReturn(new Customer());
        Order saveParam = new Order();
        saveParam.setStatus("PLACED");
        saveParam.setCustomer(new Customer());
        saveParam.setItems(Collections.emptySet());
        Set<OrderItem> orderItems = Collections.singleton(new OrderItem());

        when(dao.save(saveParam)).thenReturn(this.order);
        when(orderItemService.saveItems(orderItems)).thenReturn(new ArrayList<>(orderItems));

        Order orderParam = new Order();
        orderParam.setItems(orderItems);
        assertThat(service.placeOrder(customerId, orderParam), is(this.order));
        verify(customerService, times(1)).findById(customerId);
        verify(dao, times(1)).save(saveParam);
        verify(orderItemService, times(1)).saveItems(orderItems);
    }

    @Test
    void cancelOrder() {
        Order canceledOrder = new Order();
        canceledOrder.setStatus("CANCELED");
        when(dao.findById(customerId, id)).thenReturn(Optional.of(order));
        when(dao.save(canceledOrder)).thenReturn(order);
        service.cancelOrder(customerId, id);
        verify(dao, times(1)).save(canceledOrder);
    }
}