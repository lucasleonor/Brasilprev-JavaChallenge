package br.com.brasilprev.teste.javachallenge.controller;

import br.com.brasilprev.teste.javachallenge.model.Client;
import br.com.brasilprev.teste.javachallenge.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Client findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Client registerClient(@RequestBody Client client) {
        return service.registerClient(client);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Client updateClient(@PathVariable Integer id, @RequestBody Client client) {
        return service.updateClient(id, client);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivateClient(@PathVariable Integer id) {
        service.inactivateClient(id);
    }

}