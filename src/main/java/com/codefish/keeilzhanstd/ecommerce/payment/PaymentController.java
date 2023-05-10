package com.codefish.keeilzhanstd.ecommerce.payment;


import com.codefish.keeilzhanstd.ecommerce.payment.Payment;
import com.codefish.keeilzhanstd.ecommerce.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ecommerce/customer/{username}")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getAllByUsername(@PathVariable String username, Principal principal) {
        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(service.getByUsername(username));
    }

    @PostMapping("/payments")
    public ResponseEntity<Payment> create(@PathVariable String username, Principal principal, @RequestBody Payment payment) {
        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(service.create(username, payment));
    }

    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Payment> deleteById(@PathVariable String username, Principal principal, @PathVariable Long id) {
        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Payment> p = service.getById(id);
        if(p.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        service.delete(username, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}


