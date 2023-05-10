package com.codefish.keeilzhanstd.ecommerce.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService service;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/register")
    public ResponseEntity createCustomer(@RequestBody Customer req) {
        return service.signUpUser(req);
    }

    @PostMapping("/login")
    public Customer loginCustomer(@RequestBody Customer req) {return service.logInUser(req);}

    @GetMapping("/api/ecommerce/customer/{username}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String username, Principal principal) {

        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Customer> c = service.getOneByUsername(username);
        return c.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @PutMapping("/api/ecommerce/customer/{username}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String username, @RequestBody Customer customer, Principal principal) {

        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Customer c = service.update(username, customer);
            return ResponseEntity.ok(c);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping("/api/ecommerce/customer/{username}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable String username, Principal principal) {

        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        service.delete(username);

        return ResponseEntity.status(HttpStatus.OK).build();
    }



}

