package com.example.backend_app.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id){
        super("Could not found the user with id"+id);
    }
    
}
