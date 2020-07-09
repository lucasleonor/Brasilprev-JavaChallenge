package br.com.brasilprev.teste.javachallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.brasilprev.teste.javachallenge.model.Customer;
import br.com.brasilprev.teste.javachallenge.service.CustomerService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Customer findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Customer registerCustomer(@RequestBody Customer customer) {
        return service.registerCustomer(customer);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Customer updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        return service.updateCustomer(id, customer);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public void inactivateCustomer(@PathVariable Integer id) {
        service.inactivateCustomer(id);
    }

}