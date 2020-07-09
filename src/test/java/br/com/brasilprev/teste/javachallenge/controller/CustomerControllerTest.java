package br.com.brasilprev.teste.javachallenge.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.brasilprev.teste.javachallenge.model.Customer;
import br.com.brasilprev.teste.javachallenge.service.CustomerService;
import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService service;
    private CustomerController controller;
    private int id;
    private Customer customer;

    @BeforeEach
    void setUp() {
        controller = new CustomerController(service);
        id = new Random().nextInt();
        customer = new Customer();
        customer.setName(RandomString.make());
    }

    @Test
    void findById() {
        when(service.findById(id)).thenReturn(customer);
        assertThat(controller.findById(id), is(customer));
        verify(service, times(1)).findById(id);
    }

    @Test
    void registerCustomer() {
        when(service.registerCustomer(new Customer())).thenReturn(customer);
        assertThat(controller.registerCustomer(new Customer()), is(customer));
        verify(service, times(1)).registerCustomer(new Customer());
    }

    @Test
    void updateCustomer() {
        when(service.updateCustomer(id, new Customer())).thenReturn(customer);
        assertThat(controller.updateCustomer(id, new Customer()), is(customer));
        verify(service, times(1)).updateCustomer(id, new Customer());
    }

    @Test
    void inactivateCustomer() {
        controller.inactivateCustomer(id);
        verify(service, times(1)).inactivateCustomer(id);
    }
}