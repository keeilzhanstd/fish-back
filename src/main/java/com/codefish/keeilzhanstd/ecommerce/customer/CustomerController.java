package com.codefish.keeilzhanstd.ecommerce.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService service;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/")
    public ResponseEntity<?> health_check() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody Customer req) {
        return service.signUpUser(req);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerDTO> loginCustomer(@RequestBody Customer req) {
        return service.logInUser(req);
    }

    @GetMapping("/api/ecommerce/customer/{username}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable String username, Principal principal) {

        if (!principal.getName().equals(username)) {
            throw new BadCredentialsException("You are not authorized");
        }

        CustomerDTO c = service.getOneByUsername(username);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/api/ecommerce/customer/{username}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable String username, @RequestBody Customer customer, Principal principal) {

        if (!principal.getName().equals(username)) {
            throw new BadCredentialsException("You are not authorized");
        }

        try {
            CustomerDTO c = service.update(username, customer);
            return ResponseEntity.ok(c);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping("/api/ecommerce/customer/{username}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable String username, Principal principal) {

        if (!principal.getName().equals(username)) {
            throw new BadCredentialsException("You are not authorized to delete this resource");
        }

        service.delete(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}

