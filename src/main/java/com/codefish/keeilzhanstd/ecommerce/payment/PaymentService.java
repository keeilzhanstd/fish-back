package com.codefish.keeilzhanstd.ecommerce.payment;

import com.codefish.keeilzhanstd.ecommerce.customer.Customer;
import com.codefish.keeilzhanstd.ecommerce.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Payment create(String username, Payment payment) {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isPresent()) {
                StringBuilder sb = new StringBuilder();
                sb.append(payment.getCardNumber().substring(0, 4));
                sb.append("-****-****-");
                sb.append(payment.getCardNumber().substring(11, 15));
                payment.setHidden(sb.toString());

                List<Payment> existing = customer.get().getPayment();
                existing.add(payment);
                customer.get().setPayment(existing);
                customerRepository.save(customer.get());
                return payment;
        }
        return null;
    }

    public void delete(String username, Long payment_id) {
        Optional<Customer> existing = customerRepository.findByUsername(username);
        if (existing.isPresent()) {
            List<Payment> payments = existing.get().getPayment();

            Predicate<Payment> condition = payment1 -> payment1.getId().equals(payment_id);
            payments.removeIf(condition);

            existing.get().setPayment(payments);
            customerRepository.save(existing.get());
            paymentRepository.deleteById(payment_id);
        }
    }

    public List<Payment> getByUsername(String username) {
        Optional<Customer> existing = customerRepository.findByUsername(username);
        return existing.map(Customer::getPayment).orElse(null);
    }

    public Optional<Payment> getById(Long id) {
        Optional<Payment> existing = paymentRepository.findById(id);
        return existing;
    }

}

