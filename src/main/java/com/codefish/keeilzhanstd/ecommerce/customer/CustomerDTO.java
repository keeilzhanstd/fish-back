package com.codefish.keeilzhanstd.ecommerce.customer;

import java.util.List;

public record CustomerDTO(Long id, String username, String role, Long cart_id, List<Long> payment_id, List<Long> orders_id) {
}
