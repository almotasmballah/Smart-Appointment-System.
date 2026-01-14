/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.example.demo.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("بدء تنفيذ الدالة: {} بمؤشرات: {}", 
            joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* com.example.demo.service.*.*(..))", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        logger.info("تم إنهاء الدالة: {} بنجاح. النتيجة: {}", 
            joinPoint.getSignature().getName(), result);
    }
}
