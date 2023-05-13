package com.codefish.keeilzhanstd.ecommerce.order;

import com.codefish.keeilzhanstd.ecommerce.customer.CustomerDTO;
import com.codefish.keeilzhanstd.ecommerce.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ecommerce/customer/{username}/orders")
public class OrdController {
    @Autowired
    private OrdService service;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Ord>> getAllOrdersByUsername(@PathVariable String username, Principal principal) {
        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomerDTO c = customerService.getOneByUsername(username);

        return ResponseEntity.ok(service.getAllByUserId(c.id()));
    }

    @PostMapping
    public ResponseEntity<Ord> createOrder(@PathVariable String username, Principal principal, @RequestBody Ord order) {

        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(service.create(username, order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ord> updateOrder(@PathVariable String username, @PathVariable Long id, Principal principal, @RequestBody Ord order) {

        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomerDTO c = customerService.getOneByUsername(username);
        Optional<Ord> o = service.getOne(id);
        if(o.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(service.update(id, order));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ord> deleteOrder(@PathVariable String username, @PathVariable Long id, Principal principal) {
        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Ord> o = service.getOne(id);
        if(o.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}


