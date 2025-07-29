package com.mosh.course.controllers;

import com.mosh.course.models.Message;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Messages")
public class MessageController {
    @RequestMapping("/hello")
    public Message sayHello(){
        return new Message("Hello world!");
    }
}
