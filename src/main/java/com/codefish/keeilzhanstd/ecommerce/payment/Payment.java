package com.codefish.keeilzhanstd.ecommerce.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Setter @Getter @ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String customerId;
    private String cardNumber;
    private String hidden;
    private String monthOfExpiry;
    private String yearOfExpiry;
    private String cvv;

}
