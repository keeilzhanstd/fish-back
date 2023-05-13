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

import java.util.ArrayList;
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
    @Autowired
    private CustomerDTOMapper mapper;


    public CustomerDTO update(String username, Customer req) throws Exception {
        Optional<Customer> existing = repository.findByUsername(username);

        if (existing.isEmpty()) {
            throw new Exception("Resource Not Found");
        }

        Customer updated = existing.get();
        updated.setPassword(req.getPassword());
        updated.setUsername(req.getUsername());
        updated.setCart(req.getCart());
        updated.setRole(req.getRole());
        updated.setOrders(req.getOrders());
        updated.setPayment(req.getPayment());
        repository.save(updated);

        return mapper.apply(updated);
    }

    public void delete(String username) {
        Optional<Customer> existing = repository.findByUsername(username);

        if (existing.isEmpty()) {
            throw new BadCredentialsException("User does not exist");
        }

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

    public ResponseEntity<CustomerDTO> signUpUser(Customer user) {

        Optional<Customer> c = repository.findByUsername(user.getUsername());
        if (c.isPresent()) {
            throw new BadCredentialsException("Username already taken");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Cart cart = new Cart();
        Cart cartForUser = cartService.create(cart);

        user.setCart(cartForUser);
        user.setPayment(new ArrayList<>());
        user.setOrders(new ArrayList<>());;
        Customer userToDto = repository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.apply(userToDto));
    }

    public ResponseEntity<CustomerDTO> logInUser(Customer req) {
        Optional<Customer> c = repository.findByUsername(req.getUsername());
        if (c.isEmpty()) {
            throw new BadCredentialsException("User not found");
        }

        if (encoder.matches(req.getPassword(), c.get().getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(mapper.apply(c.get()));
        } else {
            throw new BadCredentialsException("Password mismatch");
        }
    }

    public CustomerDTO getOneByUsername(String username) {
        Optional<Customer> c = repository.findByUsername((username));
        if(c.isEmpty()){
            throw new BadCredentialsException("User not found");
        }
        return mapper.apply(c.get());
    }
}