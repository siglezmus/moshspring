package com.mosh.course;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class HeavyResource {

    public HeavyResource() {
        System.out.println("Heavy resource created");
    }
}
