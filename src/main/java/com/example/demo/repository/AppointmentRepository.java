/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.repository;

import com.example.demo.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.service.id = :serviceId " +
           "AND a.status = 'APPROVED' " +
           "AND (:newStart < a.appointmentEndDate AND :newEnd > a.appointmentStartDate)")
    boolean existsOverlapping(@Param("serviceId") Long serviceId, 
                              @Param("newStart") LocalDateTime newStart, 
                              @Param("newEnd") LocalDateTime newEnd); 
}