package br.com.brasilprev.teste.javachallenge.service;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import br.com.brasilprev.teste.javachallenge.dao.OrderItemDao;
import br.com.brasilprev.teste.javachallenge.model.OrderItem;

@Service
public class OrderItemService {
    private OrderItemDao dao;

    @Autowired
    public OrderItemService(OrderItemDao dao) {
        this.dao = dao;
    }

    public List<OrderItem> saveItems(Set<OrderItem> items) {
        try {
            return dao.saveAll(items);
        } catch (DataIntegrityViolationException | JpaObjectRetrievalFailureException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
