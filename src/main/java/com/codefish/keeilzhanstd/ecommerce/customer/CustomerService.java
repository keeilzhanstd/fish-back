package com.codefish.keeilzhanstd.ecommerce.customer;

import com.codefish.keeilzhanstd.ecommerce.CustomerAuthenticationProvider;
import com.codefish.keeilzhanstd.ecommerce.cart.Cart;
import com.codefish.keeilzhanstd.ecommerce.cart.CartService;
import com.codefish.keeilzhanstd.ecommerce.order.Ord;
import com.codefish.keeilzhanstd.ecommerce.payment.Payment;
import com.codefish.keeilzhanstd.ecommerce.cart.CartRepository;
import com.codefish.keeilzhanstd.ecommerce.order.OrdRepository;
import com.codefish.keeilzhanstd.ecommerce.payment.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private OrdRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CustomerAuthenticationProvider authenticator;
    @Autowired
    private PaymentRepository paymentRepository;


    public Customer update(String username, Customer req) throws Exception {
        Optional<Customer> existing = repository.findByUsername(username);

        if (existing.isPresent()) {
            Customer updated = existing.get();
            updated.setPassword(req.getPassword());
            updated.setUsername(req.getUsername());
            updated.setCart(req.getCart());
            updated.setRole(req.getRole());
            updated.setOrders(req.getOrders());
            updated.setPayment(req.getPayment());
            return repository.save(updated);
        }

        throw new Exception("Resource Not Found");
    }

    public void delete(String username) {
        Optional<Customer> existing = repository.findByUsername(username);

        if (existing.isPresent()) {
            //Clean all the orders linked to the user when deleting user.
            for(Ord order : existing.get().getOrders()){
                orderRepository.deleteById(order.getId());
            }

            //Clean all the payments linked to the user when deleting user.
            for(Payment payment : existing.get().getPayment()){
                paymentRepository.deleteById(payment.getId());
            }

            //Delete cart associated with this customer
            cartRepository.deleteById(existing.get().getCart().getId());

            //Safely delete user, after orders cleaned up.
            repository.delete(existing.get());
        }
    }

    public ResponseEntity signUpUser(Customer user) {

        Optional<Customer> c = repository.findByUsername(user.getUsername());
        if (c.isEmpty()) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            Cart cart = new Cart();
            Cart cartForUser = cartService.create(cart);

            user.setCart(cartForUser);
            return ResponseEntity.ok(repository.save(user));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken.");
        }
    }

    public Customer logInUser(Customer req) {
        Optional<Customer> c = repository.findByUsername(req.getUsername());
        if (c.isEmpty()) {
            throw new BadCredentialsException("Details not found");
        }

        if (encoder.matches(req.getPassword(), c.get().getPassword())) {
            return c.get();
        } else {
            throw new BadCredentialsException("Password mismatch");
        }
    }

    public Optional<Customer> getOne(Long id) {
        return repository.findById(id);
    }

    public List<Customer> getAll() {
        return repository.findAll();
    }

    public Optional<Customer> getOneByUsername(String username) {
        return repository.findByUsername(username);
    }
}