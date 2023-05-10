package com.codefish.keeilzhanstd.ecommerce.product;
import com.codefish.keeilzhanstd.ecommerce.cart.Cart;
import com.codefish.keeilzhanstd.ecommerce.category.Category;
import com.codefish.keeilzhanstd.ecommerce.order.Ord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter @Setter @ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String image;
    private BigDecimal price;
    private Boolean inStock;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToMany
    @JsonIgnore
    private List<Ord> orders;

    @ManyToMany
    @JsonIgnore
    private List<Cart> carts;

}
