package com.codefish.keeilzhanstd.ecommerce.order;

import com.codefish.keeilzhanstd.ecommerce.customer.Customer;
import com.codefish.keeilzhanstd.ecommerce.product.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter @Getter @ToString
public class Ord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private Boolean canceled;
    private String payedBy;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "ord_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

}
