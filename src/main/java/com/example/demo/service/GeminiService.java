package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final WebClient webClient;
    private final String API_KEY = "AIzaSyA1lIqUy8DkH0a8ppJQ0bOoGNpuWrgWsMM"; 

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://generativelanguage.googleapis.com").build();
    }

    public String suggestAppointment(String businessHours, List<String> bookedAppointments, String serviceType) {
        String promptText = String.format(
            "أنا نظام إدارة مواعيد ذكي. أوقات العمل: %s. المواعيد المحجوزة: %s. " +
            "الخدمة المطلوبة: %s. اقترح أفضل وقت متاح.",
            businessHours, bookedAppointments.toString(), serviceType
        );

        Map<String, Object> body = Map.of(
            "contents", List.of(Map.of("parts", List.of(Map.of("text", promptText))))
        );

        try {
            Map response = webClient.post()
                .uri("/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

            List candidates = (List) response.get("candidates");
            Map firstCandidate = (Map) candidates.get(0);
            Map content = (Map) firstCandidate.get("content");
            List parts = (List) content.get("parts");
            Map firstPart = (Map) parts.get(0);
            
            return firstPart.get("text").toString();
        } catch (Exception e) {
            return "فشل النظام في الحصول على اقتراح: " + e.getMessage();
        }
    }
}