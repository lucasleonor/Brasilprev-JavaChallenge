package br.com.brasilprev.teste.javachallenge.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.brasilprev.teste.javachallenge.model.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {
}
