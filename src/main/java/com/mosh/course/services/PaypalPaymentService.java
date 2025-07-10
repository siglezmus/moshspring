package com.mosh.course.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("Paypal")
public class PaypalPaymentService implements PaymentService{

    @Override
    public void processPayment(double amount) {
        //apiurl
        //apikey
        System.out.println("Paypal");
        System.out.println("Amount:" + amount);
    }
}
