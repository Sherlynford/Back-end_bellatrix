package com.example.backend_app.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_app.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    
}

