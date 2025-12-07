package com.bleedcode.bean_concepts_demo.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bleedcode.bean_concepts_demo.services.PaymentService;

@Component
public class PaymentProcessor {

    private final PaymentService paymentService;

    //Bean will be injected based on the qualifier
    // @Autowired
    // public PaymentProcessor(@Qualifier("upiPaymentService") PaymentService paymentService) {
    //     this.paymentService = paymentService;
    // }

    //Bean will be injected based on the property defined in application.yaml
    @Autowired
    public PaymentProcessor(PaymentService paymentService) {
        
        this.paymentService = paymentService;
    }

    public void processPayment(double amount) {
        paymentService.pay(amount);
    }

}
