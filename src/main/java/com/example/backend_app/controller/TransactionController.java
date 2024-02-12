package com.example.backend_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


import com.example.backend_app.model.Transaction;
import com.example.backend_app.repository.TransactionRepository;

@RestController
@CrossOrigin("http://localhost:5173")
public class TransactionController {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @SuppressWarnings("null")
    @PostMapping("/transactions")
    Transaction newTransaction(@RequestBody Transaction newTransaction){
        return this.transactionRepository.save(newTransaction);
    }

    @GetMapping("/transactions")
    List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    @SuppressWarnings("null")
    @GetMapping("/transactions/{id}")
    Transaction getTransactionById(@PathVariable Long id){
        return transactionRepository.findById(id)
                .orElseThrow();
    }

    @SuppressWarnings("null")
    @PutMapping("/transactions/{id}")
    Transaction updateTransaction(@RequestBody Transaction newTransaction, @PathVariable Long id){
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setProductAccount(newTransaction.getProductAccount());
                    transaction.setPrice(newTransaction.getPrice());
                    transaction.setTime(newTransaction.getTime());
                    transaction.setStatus(newTransaction.getStatus());
                    transaction.setAmount(newTransaction.getAmount());
                    return transactionRepository.save(transaction);
                }).orElseThrow();
    }

    @SuppressWarnings("null")
    @DeleteMapping("/transactions/{id}")
    ResponseEntity<Object> deleteTransaction(@PathVariable Long id){
        if (!transactionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        transactionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
