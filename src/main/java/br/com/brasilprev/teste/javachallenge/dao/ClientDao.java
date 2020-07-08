package br.com.brasilprev.teste.javachallenge.dao;

import br.com.brasilprev.teste.javachallenge.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDao extends JpaRepository<Client, Integer> {
}
