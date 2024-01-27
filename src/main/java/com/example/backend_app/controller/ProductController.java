package com.example.backend_app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.backend_app.exception.ProductNotFoundException;
import com.example.backend_app.model.Product;
import com.example.backend_app.repository.ProductRepository;

@RestController
@CrossOrigin("http://localhost:5173")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/product")
    
    Product newProduct(@RequestBody Product newProduct){
        return this.productRepository.save(newProduct);
    }
    @GetMapping("/products")
    List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable Long id){
        return productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));
    }
    @PutMapping("/product/{id}")
    Product updateProduct(@RequestBody Product newProduct,@PathVariable Long id){
        return productRepository.findById(id)
        .map(product -> {
            product.setName(newProduct.getName());
            product.setType(newProduct.getType());
            product.setPicture(newProduct.getPicture());
            product.setUnit(newProduct.getUnit());
            product.setDescription(newProduct.getDescription());
            product.setAmount(newProduct.getAmount());
            product.setBuying_price(newProduct.getBuying_price());
            product.setSelling_price(newProduct.getSelling_price());

            return productRepository.save(product);
        }).orElseThrow(()-> new ProductNotFoundException(id));
    }
    @DeleteMapping("/product/{id}")
    String deleteProduct(@PathVariable Long id){
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundException(id);
        }else{
            productRepository.deleteById(id);
            return "Product with id"+id +"has been deleted";
        }
    }
}
