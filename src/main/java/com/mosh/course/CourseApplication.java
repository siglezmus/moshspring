package com.mosh.course;

import com.mosh.course.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;

@SpringBootApplication
public class CourseApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CourseApplication.class, args);
		OrderService orderService = context.getBean(OrderService.class);
		UserService service = context.getBean(UserService.class);
		service.registerUser(new User(0, "heya@mail.to", "211", "Erik"));

		//var heavyResource = context.getBean(HeavyResource.class);
		orderService.placeOrder();
		//context.close();
	}

}
