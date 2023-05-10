package com.codefish.keeilzhanstd.ecommerce.order;

import com.codefish.keeilzhanstd.ecommerce.customer.Customer;
import com.codefish.keeilzhanstd.ecommerce.order.Ord;
import com.codefish.keeilzhanstd.ecommerce.customer.CustomerRepository;
import com.codefish.keeilzhanstd.ecommerce.order.OrdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrdService {
    @Autowired
    private OrdRepository ordRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Ord create(String username, Ord order) {

        Optional< Customer > existing = customerRepository.findByUsername(username);
        if (existing.isPresent()) {
            existing.get().getOrders().add(order);
            order.setCustomer(existing.get());
            customerRepository.save(existing.get());

            return ordRepository.save(order);
        }

        return null;
    }

    public Ord update(Long id, Ord order) {
        Optional<Ord> existing = ordRepository.findById(id);

        if (existing.isPresent()) {
            Ord updated = existing.get();
            updated.setProducts(order.getProducts());
            updated.setCanceled(order.getCanceled());
            updated.setDate(order.getDate());
            updated.setCustomer(order.getCustomer());
            return ordRepository.save(updated);
        }

        return null;
    }

    public void delete(Long id) {
        Optional<Ord> existing = ordRepository.findById(id);
        existing.ifPresent(ord -> ordRepository.delete(ord));
    }

    public Optional<Ord> getOne(Long id) { return ordRepository.findById(id);}

    public List<Ord> getAllByUserId(Long id) {
        return ordRepository.findByCustomer_Id(id);
    }
}

