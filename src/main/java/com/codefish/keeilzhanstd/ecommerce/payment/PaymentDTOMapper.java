package com.codefish.keeilzhanstd.ecommerce.payment;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PaymentDTOMapper implements Function<Payment, PaymentDTO> {
        @Override
        public PaymentDTO apply(Payment payment) {
            return new PaymentDTO(
                    payment.getId(),
                    payment.getHidden(),
                    payment.getMonthOfExpiry(),
                    payment.getYearOfExpiry());
        }
}
