package com.example.backend_app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_app.model.ProductAccount;
import com.example.backend_app.model.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByProductAccount(ProductAccount productAccount);
    
}

