package br.com.brasilprev.teste.javachallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import br.com.brasilprev.teste.javachallenge.dao.CustomerDao;
import br.com.brasilprev.teste.javachallenge.model.Customer;

@Service
public class CustomerService {
    private final CustomerDao dao;

    @Autowired
    public CustomerService(CustomerDao dao) {
        this.dao = dao;
    }

    public Customer findById(Integer id) {
        // an inactive customer is the same as a deleted customer
        // I've chosen to make it inactive over deleting to preserve the order history
        return dao.findById(id).filter(Customer::getActive)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Customer registerCustomer(Customer customer) {
        customer.setId(null);
        customer.setActive(true);
        return dao.save(customer);
    }

    public Customer updateCustomer(Integer id, Customer customer) {
        findById(id);
        customer.setId(id);
        customer.setActive(true);
        return dao.save(customer);
    }

    public void inactivateCustomer(Integer id) {
        Customer customer = findById(id);
        customer.setActive(false);
        dao.save(customer);
    }
}
