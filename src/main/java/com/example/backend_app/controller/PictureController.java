package com.example.backend_app.controller;

import com.example.backend_app.model.Picture;
import com.example.backend_app.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/pictures")
public class PictureController {

    @Autowired
    private PictureRepository pictureRepository;

    // Create
    @PostMapping
    public ResponseEntity<Picture> createPicture(@RequestBody Picture picture) {
        Picture savedpicture = pictureRepository.save(picture);
        return ResponseEntity.ok(savedpicture);
    }

    // Read All
    @GetMapping
    public ResponseEntity<List<Picture>> getAllPicture() {
        List<Picture> picture = pictureRepository.findAll();
        return ResponseEntity.ok(picture);
    }

    // Read One
    @GetMapping("/{id}")
    public ResponseEntity<Picture> getPictureById(@PathVariable Long id) {
        Optional<Picture> pictureOptional = pictureRepository.findById(id);
        return pictureOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Picture> updatePicture(@PathVariable Long id, @RequestBody Picture updatedPicture) {
        if (!pictureRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        updatedPicture.setId(id);
        Picture savedPicture = pictureRepository.save(updatedPicture);
        return ResponseEntity.ok(savedPicture);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePicture(@PathVariable Long id) {
        if (!pictureRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        pictureRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}