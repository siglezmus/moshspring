package com.mosh.course.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Service
public class OrderService {

    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
        System.out.println("Order service created");
    }

    @PostConstruct
    public void init(){
        System.out.println("OS post construct");
    }

    @PreDestroy
    public void cleanup(){
        System.out.println("OS pre destroy");
    }

    public void placeOrder(){
        paymentService.processPayment(10);
    }

}
