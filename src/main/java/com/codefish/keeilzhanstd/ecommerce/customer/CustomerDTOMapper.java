package com.codefish.keeilzhanstd.ecommerce.customer;

import org.springframework.stereotype.Service;

import com.codefish.keeilzhanstd.ecommerce.payment.Payment;
import com.codefish.keeilzhanstd.ecommerce.order.Ord;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerDTOMapper implements Function<Customer, CustomerDTO> {

        @Override
        public CustomerDTO apply(Customer customer) {
            return new CustomerDTO(
                    customer.getId(),
                    customer.getUsername(),
                    customer.getRole(),
                    customer.getCart().getId(),
                    customer.getPayment()
                            .stream()
                            .map(Payment::getId)
                            .collect(Collectors.toList()),
                    customer.getOrders()
                            .stream()
                            .map(Ord::getId)
                            .collect(Collectors.toList())
            );
        }
}
