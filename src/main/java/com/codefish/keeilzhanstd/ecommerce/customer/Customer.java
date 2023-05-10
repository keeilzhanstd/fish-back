package com.codefish.keeilzhanstd.ecommerce.customer;

import com.codefish.keeilzhanstd.ecommerce.cart.Cart;
import com.codefish.keeilzhanstd.ecommerce.order.Ord;
import com.codefish.keeilzhanstd.ecommerce.payment.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String username;
    private String password;
    private String role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="payment_id")
    private List<Payment> payment;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Ord> orders;

}