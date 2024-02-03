package com.example.backend_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Picture {

    @Id
    @Column(name = "ID_img")
    private Long id;

     @Column(name = "url")
    private String url; 
    
}
