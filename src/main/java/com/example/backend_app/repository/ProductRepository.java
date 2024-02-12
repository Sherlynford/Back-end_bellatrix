package com.example.backend_app.repository;


// import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

import com.example.backend_app.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // @Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.productAccounts pa LEFT JOIN FETCH pa.transactions WHERE p.id = :id")
    // Product findByIdWithProductAccountAndTransactions(@Param("id") Long id);
    
    // @Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.productAccounts pa LEFT JOIN FETCH pa.transactions")
    // List<Product> findAllWithProductAccountAndTransactions();
        
    Optional<Product> findByNameAndUnit(String name, String unit);
}

