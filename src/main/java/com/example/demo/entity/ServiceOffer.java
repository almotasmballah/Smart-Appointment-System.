/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*; // استيراد مكتبة التحقق
import lombok.Data;

@Entity
@Data
public class ServiceOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "اسم الخدمة لا يمكن أن يكون فارغاً")
    @Size(min = 3, max = 50, message = "اسم الخدمة يجب أن يكون بين 3 و 50 حرفاً")
    private String name;

    @NotBlank(message = "وصف الخدمة مطلوب")
    private String description;

    @Min(value = 10, message = "مدة الخدمة لا يمكن أن تقل عن 10 دقائق")
    private int durationMinutes; 

    @Positive(message = "يجب أن يكون السعر رقماً موجباً")
    private double price;
}