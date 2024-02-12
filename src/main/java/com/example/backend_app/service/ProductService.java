package com.example.backend_app.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_app.model.Product;
import com.example.backend_app.model.ProductAccount;
import com.example.backend_app.model.Transaction;
import com.example.backend_app.repository.ProductAccountRepository;
import com.example.backend_app.repository.ProductRepository;
import com.example.backend_app.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAccountRepository productAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @SuppressWarnings("unused")
    @Transactional
    public void addProduct(Product product) {

    Optional<Product> existingProductOptional = productRepository.findByNameAndUnit(product.getName(), product.getUnit());
    if (existingProductOptional.isPresent()) {
        throw new RuntimeException("Product with the same name and unit already exists");
    }

        boolean isAddingProduct = true; 

        if (product.getAmount() < 0) {
            isAddingProduct = false; 
        }

        Product savedProduct = productRepository.save(product);

        ProductAccount productAccount = new ProductAccount();
        productAccount.setProduct(savedProduct);
        productAccount.setUnit(product.getUnit());

        if (productAccount.getTotal() > 0){
            productAccount.setProfit(true);
        } else{
            productAccount.setProfit(false);
        }
    
        if (isAddingProduct = true) {
            productAccount.setRemainQuantity(productAccount.getRemainQuantity() + product.getAmount());
            productAccount.setTotal(product.getAmount() * product.getBuying_price());
        } else {
            productAccount.setRemainQuantity(productAccount.getRemainQuantity() - product.getAmount());
            productAccount.setTotal(product.getAmount() * product.getSelling_price());
        }


        ProductAccount savedProductAccount = productAccountRepository.save(productAccount);

        Transaction transaction = new Transaction();
        
        transaction.setProductAccount(savedProductAccount);
        if (isAddingProduct = true) {
            transaction.setPrice(product.getBuying_price());
            transaction.setStatus("buying");
        } else{
            transaction.setPrice(product.getSelling_price());
            transaction.setStatus("selling");

        }
        transaction.setTime(Instant.now());
        transaction.setAmount(product.getAmount());
        transactionRepository.save(transaction);
    }
    // public List<Product> getAllProducts() {
    //     return productRepository.findAllWithProductAccountAndTransactions();
    // }
    
    // public Optional<Product> getProductById(Long id) {
    //     return Optional.ofNullable(productRepository.findByIdWithProductAccountAndTransactions(id));
    // }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @SuppressWarnings("null")
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    

    @Transactional
    public void updateProduct(Long id, Product updatedProduct) {
        @SuppressWarnings("null")
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setType(updatedProduct.getType());
            existingProduct.setPicture(updatedProduct.getPicture());
            existingProduct.setUnit(updatedProduct.getUnit());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setBuying_price(updatedProduct.getBuying_price());
            existingProduct.setSelling_price(updatedProduct.getSelling_price());
            existingProduct.setAmount(updatedProduct.getAmount());

            productRepository.save(existingProduct);
            
            ProductAccount productAccount = productAccountRepository.findByProduct(existingProduct);
            if (productAccount != null) {

                productAccount.getProduct().setUnit(updatedProduct.getUnit());

                int amountDifference = updatedProduct.getAmount() - existingProduct.getAmount();
                
                productAccount.setRemainQuantity(productAccount.getRemainQuantity() + amountDifference);
                productAccount.setTotal(productAccount.getTotal() + (amountDifference * getTransactionPrice(existingProduct, amountDifference))); 
                productAccountRepository.save(productAccount);
    
                Transaction transaction = new Transaction();
                transaction.setProductAccount(productAccount);
                transaction.setPrice(getTransactionPrice(existingProduct, amountDifference));
                transaction.setStatus(amountDifference > 0 ? "buying" : "selling");
                transaction.setTime(Instant.now());
                transaction.setAmount(Math.abs(amountDifference));
                transactionRepository.save(transaction);
            } else {
                throw new RuntimeException("Product Account not found for product with id: " + existingProduct.getId());
            }
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
    
    private Double getTransactionPrice(Product product, int amountDifference) {
        if (amountDifference > 0) {
            return product.getBuying_price();
        } else {
            return product.getSelling_price();
        }
    }

    @SuppressWarnings("null")
    @Transactional
    public void deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            
            ProductAccount productAccount = productAccountRepository.findByProduct(product);
            
            List<Transaction> transactions = transactionRepository.findByProductAccount(productAccount);
            transactionRepository.deleteAll(transactions);
            
            productAccountRepository.delete(productAccount);
            
            productRepository.deleteById(id);
        } else {

            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}
