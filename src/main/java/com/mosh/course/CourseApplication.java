package com.mosh.course;

import com.mosh.course.services.NotificationManager;
import com.mosh.course.services.OrderService;
import com.mosh.course.services.PaypalPaymentService;
import com.mosh.course.services.StripePaymentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;

@SpringBootApplication
public class CourseApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(CourseApplication.class, args);
		OrderService orderService = context.getBean(OrderService.class);
		orderService.placeOrder();
	}

}
