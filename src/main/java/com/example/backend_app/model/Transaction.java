package com.example.backend_app.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_account_id", referencedColumnName = "accountId")
    @JsonBackReference
    private ProductAccount productAccount;

    private Double price;

    private Instant time;

    private String status;
    
    private Integer amount;
}
