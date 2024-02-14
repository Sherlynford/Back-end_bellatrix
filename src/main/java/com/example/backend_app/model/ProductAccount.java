package com.example.backend_app.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductAccount {

    @Id
    @GeneratedValue
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonBackReference
    private Product product;

    private String unit;

    private Double total = 0.0;
    
    private Boolean profit; 
    
    private Integer remainQuantity = 0;

    @OneToMany(mappedBy = "productAccount", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Transaction> transactions;
}
