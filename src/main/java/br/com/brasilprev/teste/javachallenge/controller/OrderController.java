package br.com.brasilprev.teste.javachallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.brasilprev.teste.javachallenge.model.Order;
import br.com.brasilprev.teste.javachallenge.service.OrderService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/customers/{customerId}/orders")
public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiResponses(value = {
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ApiImplicitParams({
      @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
        value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
      @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
        value = "Number of records per page.", defaultValue = "5"),
      @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
        value = "Sorting criteria in the format: property(,asc|desc). " +
          "Default sort order is ascending. " +
          "Multiple sort criteria are supported.")
    })
    public Page<Order> listOrders(@PathVariable Integer customerId,
                                  @ApiIgnore("Ignored because swagger ui shows the wrong params") Pageable pageable) {
        return service.listOrders(customerId, pageable);
    }

    @RequestMapping(value = "{orderId}", method = RequestMethod.GET)
    @ApiResponses(value = {
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Order findById(@PathVariable Integer customerId, @PathVariable Integer orderId) {
        return service.findById(customerId, orderId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Order placeOrder(@PathVariable Integer customerId, @RequestBody Order order) {
        return service.placeOrder(customerId, order);
    }

    @RequestMapping(value = "{orderId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public void cancelOrder(@PathVariable Integer customerId, @PathVariable Integer orderId) {
        service.cancelOrder(customerId, orderId);
    }

}