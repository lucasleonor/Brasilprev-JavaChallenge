package br.com.brasilprev.teste.javachallenge.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import br.com.brasilprev.teste.javachallenge.model.Order;
import br.com.brasilprev.teste.javachallenge.service.OrderService;
import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService service;
    private OrderController controller;
    private int id;
    private Order order;
    private int customerId;

    @BeforeEach
    void setUp() {
        controller = new OrderController(service);
        id = new Random().nextInt();
        customerId = new Random().nextInt();
        order = new Order();
        order.setStatus(RandomString.make());
    }

    @Test
    void listOrders() {
        int randomInt = Math.abs(new Random().nextInt());
        PageRequest pageRequest = PageRequest.of(randomInt, randomInt);
        PageImpl<Order> ordersPage = new PageImpl<>(Collections.singletonList(order));

        when(service.listOrders(customerId, pageRequest)).thenReturn(ordersPage);
        assertThat(controller.listOrders(customerId, pageRequest), is(ordersPage));
        verify(service, times(1)).listOrders(customerId, pageRequest);
    }

    @Test
    void findById() {
        when(service.findById(customerId, id)).thenReturn(order);
        assertThat(controller.findById(customerId, id), is(order));
        verify(service, times(1)).findById(customerId, id);
    }

    @Test
    void placeOrder() {
        when(service.placeOrder(customerId, new Order())).thenReturn(order);
        assertThat(controller.placeOrder(customerId, new Order()), is(order));
        verify(service, times(1)).placeOrder(customerId, new Order());
    }

    @Test
    void cancelOrder() {
        controller.cancelOrder(customerId, id);
        verify(service, times(1)).cancelOrder(customerId, id);
    }
}