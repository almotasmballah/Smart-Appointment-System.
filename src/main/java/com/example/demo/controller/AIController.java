package com.example.demo.controller;

import com.example.demo.service.GeminiService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private GeminiService geminiService;

    @GetMapping("/suggest")
    public ResponseEntity<String> getAISuggestion(@RequestParam String serviceName) {
        String suggestion = geminiService.suggestAppointment("9 AM - 5 PM", List.of("10:00-10:45"), serviceName);
        return ResponseEntity.ok(suggestion);
    }
}