package com.codefish.keeilzhanstd.ecommerce.order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdRepository extends JpaRepository<Ord, Long> {
    @Query("select o from Ord o where o.customer.id = ?1")
    List<Ord> findByCustomer_Id(Long id);
}
