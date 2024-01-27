package com.example.backend_app.model;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_account_id", nullable = false)
    private ProductAccount productAccount;

    private String status;

    @Transient
    private Integer amount;

    @Transient
    private Double price;

    private Instant time;

    @PostLoad
    public void calculateDerivedValues() {
        if (productAccount != null && productAccount.getProduct() != null) {
            this.amount = productAccount.getProduct().getAmount();
            this.price = productAccount.getProduct().getSelling_price();
        }
    }

}
