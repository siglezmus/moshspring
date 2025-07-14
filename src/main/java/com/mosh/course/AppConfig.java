package com.mosh.course;

import com.mosh.course.services.OrderService;
import com.mosh.course.services.PaymentService;
import com.mosh.course.services.PaypalPaymentService;
import com.mosh.course.services.StripePaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Value("${payment.provider}")
    private String paymentProvider;

    @Bean
    public PaymentService stripe(){
        return new StripePaymentService();
    }

    @Bean
    public  PaymentService paypal(){
        return new PaypalPaymentService();
    }

    @Bean
    public OrderService orderService(){
        return paymentProvider.equals("paypal") ? new OrderService(paypal()) : new OrderService(stripe());
    }
}
