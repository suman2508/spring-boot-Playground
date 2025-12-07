package com.bleedcode.bean_concepts_demo.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@Qualifier("upiPaymentService")
@ConditionalOnProperty(prefix = "payment", name = "enabled", havingValue = "upi")
public class UpiPaymentService implements PaymentService {
    
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using UPI Payment Service");
    }
}
