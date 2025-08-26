package com.mosh.course.payments;

import lombok.Data;

@Data
public class OrderResponse {
    public Long id;
    private String url;

    public OrderResponse(Long orderId, String url){
        this.id = orderId;
        this.url = url;
    }
}
