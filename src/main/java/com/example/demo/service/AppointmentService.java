package com.example.demo.service;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.ServiceOffer;
import com.example.demo.entity.User;
import com.example.demo.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.time.LocalDateTime;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; 

    @Transactional
    public Appointment bookSmart(User customer, ServiceOffer service, LocalDateTime startDate) {
        LocalDateTime endDate = startDate.plusMinutes(service.getDurationMinutes());
        var day = startDate.getDayOfWeek();
        if (day == java.time.DayOfWeek.FRIDAY || day == java.time.DayOfWeek.SATURDAY) {
            throw new RuntimeException("نعتذر، لا يمكن الحجز في أيام العطلة (الجمعة والسبت)");
        }
        if (startDate.getHour() < 9 || endDate.getHour() >= 17) {
            throw new RuntimeException("خارج أوقات العمل (9 ص - 5 م)");
        }

        boolean exists = appointmentRepository.existsOverlapping(service.getId(), startDate, endDate);

        if (exists) {
            throw new RuntimeException("هذا الوقت محجوز مسبقاً لموعد آخر");
        }

        Appointment app = new Appointment();
        app.setCustomer(customer);
        app.setService(service);
        app.setAppointmentStartDate(startDate);
        app.setAppointmentEndDate(endDate);
        app.setStatus("PENDING");

        messagingTemplate.convertAndSend("/topic/notifications", "تم طلب موعد جديد لخدمة: " + service.getName());

        return appointmentRepository.save(app);
    }

    @Transactional 
    public void updateStatus(Long id, String status) {
        Appointment app = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("الموعد غير موجود"));

        app.setStatus(status);
        appointmentRepository.save(app);

        String message = "تنبيه: حالة موعدك رقم (" + id + ") أصبحت الآن: " + status;
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
