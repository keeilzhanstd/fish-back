package com.codefish.keeilzhanstd.ecommerce.cart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/ecommerce/customer/{username}/cart")
public class CartController {

    @Autowired
    private CartService service;

    @GetMapping
    public ResponseEntity<Cart> get(@PathVariable String username, Principal principal) {
        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long idt = service.getIdByUsername(username);

        Optional<Cart> c = service.get(idt);
        return c.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PutMapping
    public ResponseEntity<Cart> update(@PathVariable String username, Principal principal, @RequestBody Cart cart) {
        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long idt = service.getIdByUsername(username);
        return ResponseEntity.ok(service.update(idt, cart));

    }

    @PutMapping("/clear")
    public ResponseEntity<Void> clear(@PathVariable String username, Principal principal) {
        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long idt = service.getIdByUsername(username);
        Optional<Cart> c = service.get(idt);
        if(c.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        service.clearCart(idt);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

}


