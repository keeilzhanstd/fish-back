package com.codefish.keeilzhanstd.ecommerce.cart;

import com.codefish.keeilzhanstd.ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Setter @Getter @ToString
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "carts_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

}
