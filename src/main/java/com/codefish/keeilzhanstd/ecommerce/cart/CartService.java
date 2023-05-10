package com.codefish.keeilzhanstd.ecommerce.cart;

import com.codefish.keeilzhanstd.ecommerce.customer.Customer;
import com.codefish.keeilzhanstd.ecommerce.product.Product;
import com.codefish.keeilzhanstd.ecommerce.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Cart create(Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> get(Long id) {
        Optional<Cart> c = cartRepository.findById(id);
        if (c.isPresent()){
            return c;
        }
        return null;
    }

    public Cart update(Long id, Cart cart) {
        Optional<Cart> existing = cartRepository.findById(id);
        if (existing.isPresent()) {
            Cart updated = existing.get();
            updated.setProducts(cart.getProducts());
            return cartRepository.save(updated);
        }
        return null;
    }

    public void clearCart(Long id) {
        Optional<Cart> existing = cartRepository.findById(id);
        if (existing.isPresent()) {
            Cart updated = existing.get();
            updated.setProducts(new ArrayList<Product>());
            cartRepository.save(updated);
        }

    }

    public Long getIdByUsername(String username) {
        Optional<Customer> c = customerRepository.findByUsername(username);
        return c.map(customer -> customer.getCart().getId()).orElse(null);
    }
}

