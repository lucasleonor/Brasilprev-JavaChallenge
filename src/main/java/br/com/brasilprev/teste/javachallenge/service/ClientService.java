package br.com.brasilprev.teste.javachallenge.service;

import br.com.brasilprev.teste.javachallenge.dao.ClientDao;
import br.com.brasilprev.teste.javachallenge.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class ClientService {
    private final ClientDao dao;

    @Autowired
    public ClientService(ClientDao dao) {
        this.dao = dao;
    }

    public Client findById(Integer id) {
        return dao.findById(id).filter(Client::isActive)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Client registerClient(Client client) {
        client.setId(null);
        return dao.save(client);
    }

    public Client updateClient(Integer id, Client client) {
        findById(id);
        client.setId(id);
        return dao.save(client);
    }

    public void inactivateClient(Integer id) {
        Client client = findById(id);
        client.setActive(false);
        dao.save(client);
    }
}
