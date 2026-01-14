package com.example.demo.controller;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.ServiceOffer;
import com.example.demo.entity.User;
import com.example.demo.dto.AppointmentRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired private AppointmentService appointmentService;
    @Autowired private UserRepository userRepository;
    @Autowired private ServiceRepository serviceRepository;

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequest request, Authentication authentication) {
        try {
            User user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            ServiceOffer service = serviceRepository.findById(request.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found"));

            Appointment saved = appointmentService.bookSmart(user, service, request.getAppointmentDate());
            return ResponseEntity.ok("تم طلب الحجز بنجاح! رقم الحجز: " + saved.getId());
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveAppointment(@PathVariable Long id) {
        try {
            appointmentService.updateStatus(id, "APPROVED");
            return ResponseEntity.ok("تمت الموافقة على الموعد رقم: " + id);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectAppointment(@PathVariable Long id) {
        try {
            appointmentService.updateStatus(id, "REJECTED");
            return ResponseEntity.ok("تم رفض الموعد رقم: " + id);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}