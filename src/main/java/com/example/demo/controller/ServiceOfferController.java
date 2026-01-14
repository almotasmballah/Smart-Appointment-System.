/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.demo.controller;
import com.example.demo.entity.ServiceOffer;
import com.example.demo.repository.ServiceRepository; 
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/services")
public class ServiceOfferController {

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addService(@Valid @RequestBody ServiceOffer service) {
        serviceRepository.save(service);
        return ResponseEntity.ok("Service added successfully!");
    }

    @GetMapping("/all")
    public List<ServiceOffer> getAllServices() {
        return serviceRepository.findAll();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        return serviceRepository.findById(id).map(service -> {
        serviceRepository.delete(service);
          return ResponseEntity.ok("Service deleted successfully!");
    }).orElse(ResponseEntity.notFound().build());

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateService(@PathVariable Long id, @RequestBody ServiceOffer newServiceData) {
    return serviceRepository.findById(id).map(service -> {
        service.setName(newServiceData.getName());
        service.setDescription(newServiceData.getDescription());
        service.setPrice(newServiceData.getPrice());
        service.setDurationMinutes(newServiceData.getDurationMinutes());
        serviceRepository.save(service);
        return ResponseEntity.ok("Service updated successfully!");
    }).orElse(ResponseEntity.notFound().build());
}
    
}