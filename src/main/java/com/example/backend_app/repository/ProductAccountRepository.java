package com.example.backend_app.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_app.model.Product;
import com.example.backend_app.model.ProductAccount;


public interface ProductAccountRepository extends JpaRepository<ProductAccount,Long> {

    ProductAccount findByProduct(Product existingProduct);
    
}

