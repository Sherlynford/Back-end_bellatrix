package com.example.backend_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


import com.example.backend_app.model.ProductAccount;
import com.example.backend_app.repository.ProductAccountRepository;

@RestController
@CrossOrigin("http://localhost:5173")
public class ProductAccountController {
    
    @Autowired
    private ProductAccountRepository productAccountRepository;

    @SuppressWarnings("null")
    @PostMapping("/product-account")
    ProductAccount newProductAccount(@RequestBody ProductAccount newProductAccount){
        return this.productAccountRepository.save(newProductAccount);
    }

    @GetMapping("/product-accounts")
    public List<ProductAccount> getAllProductAccounts(){
        return productAccountRepository.findAll();
    }

    @SuppressWarnings("null")
    @GetMapping("/product-account/{id}")
    ProductAccount getProductAccountById(@PathVariable Long id){
        return productAccountRepository.findById(id)
                .orElseThrow();
    }

    @SuppressWarnings("null")
    @PutMapping("/product-account/{id}")
    ProductAccount updateProductAccount(@RequestBody ProductAccount newProductAccount, @PathVariable Long id){
        return productAccountRepository.findById(id)
                .map(productAccount -> {
                    productAccount.setProduct(newProductAccount.getProduct());
                    productAccount.setUnit(newProductAccount.getUnit());
                    productAccount.setTotal(newProductAccount.getTotal());
                    productAccount.setProfit(newProductAccount.getProfit());
                    return productAccountRepository.save(productAccount);
                }).orElseThrow();
    }

    @SuppressWarnings("null")
    @DeleteMapping("/product-account/{id}")
    ResponseEntity<Object> deleteProductAccount(@PathVariable Long id){
        if (!productAccountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productAccountRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
