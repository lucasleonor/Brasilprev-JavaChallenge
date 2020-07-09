package br.com.brasilprev.teste.javachallenge.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import br.com.brasilprev.teste.javachallenge.dao.CustomerDao;
import br.com.brasilprev.teste.javachallenge.model.Customer;
import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao dao;
    private CustomerService service;
    private int id;
    private Customer customer;

    @BeforeEach
    void setUp() {
        service = new CustomerService(dao);
        id = new Random().nextInt();
        customer = new Customer();
        customer.setName(RandomString.make());
        customer.setActive(true);
    }

    @Test
    void findById() {
        when(dao.findById(id)).thenReturn(Optional.of(customer));
        assertThat(service.findById(id), is(customer));
        verify(dao, times(1)).findById(id);
    }

    @Test
    void findById_notInBase() {
        when(dao.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.findById(id));
    }

    @Test
    void findById_inactive() {
        when(dao.findById(id)).thenReturn(Optional.of(customer));
        customer.setActive(false);
        assertThrows(ResponseStatusException.class, () -> service.findById(id));
    }

    @Test
    void registerCustomer() {
        when(dao.save(new Customer())).thenReturn(customer);
        assertThat(service.registerCustomer(new Customer()), is(customer));
        verify(dao, times(1)).save(new Customer());
    }

    @Test
    void updateCustomer() {
        Customer customerWithId = new Customer();
        customerWithId.setId(id);
        when(dao.findById(id)).thenReturn(Optional.of(customer));
        when(dao.save(customerWithId)).thenReturn(customer);
        assertThat(service.updateCustomer(id, new Customer()), is(customer));
        verify(dao, times(1)).save(customerWithId);
    }

    @Test
    void inactivateCustomer() {
        Customer customer = mock(Customer.class);
        when(customer.getActive()).thenReturn(true);
        when(dao.findById(id)).thenReturn(Optional.of(customer));
        service.inactivateCustomer(id);
        verify(dao, times(1)).save(customer);
        verify(customer, times(1)).setActive(false);
    }
}