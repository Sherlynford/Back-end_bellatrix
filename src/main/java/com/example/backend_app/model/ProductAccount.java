package com.example.backend_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductAccount {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String unit;

    @Column(unique = true)
    private Integer total;
    
    private Boolean profit; 
    
    @Transient
    private Integer remainQuantity;

  
}

