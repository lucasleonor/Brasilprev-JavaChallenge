package br.com.brasilprev.teste.javachallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import br.com.brasilprev.teste.javachallenge.dao.ProductDao;
import br.com.brasilprev.teste.javachallenge.model.Product;

@Service
public class ProductService {
    private final ProductDao dao;

    @Autowired
    public ProductService(ProductDao dao) {
        this.dao = dao;
    }

    public Page<Product> listProducts(Product filter, Pageable pageable) {
        return dao.findAll(Example.of(filter), pageable);
    }

    public Product findById(Integer id) {
        return dao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Product registerProduct(Product product) {
        product.setId(null);
        return dao.save(product);
    }

    public Product updateProduct(Integer id, Product product) {
        findById(id);
        product.setId(id);
        return dao.save(product);
    }
}