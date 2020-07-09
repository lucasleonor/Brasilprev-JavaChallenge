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
import br.com.brasilprev.teste.javachallenge.model.Product;
import br.com.brasilprev.teste.javachallenge.service.ProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiResponses(value = {
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
    public Page<Product> listProducts(Product filter,
                                      @ApiIgnore("Ignored because swagger ui shows the wrong params") Pageable pageable) {
        return service.listProducts(filter, pageable);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Product findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Product registerProduct(@RequestBody Product product) {
        return service.registerProduct(product);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ApiResponses(value = {
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return service.updateProduct(id, product);
    }


}