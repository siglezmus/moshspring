package com.mosh.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CourseApplication {

	public static void main(String[] args) {


		ConfigurableApplicationContext context = SpringApplication.run(CourseApplication.class, args);

	}

}
