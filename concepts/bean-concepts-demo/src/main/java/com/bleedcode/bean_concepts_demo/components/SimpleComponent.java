package com.bleedcode.bean_concepts_demo.components;

import org.springframework.stereotype.Component;

@Component
public class SimpleComponent {
    public SimpleComponent() {
        System.out.println("SimpleComponent instance created: " + this);
    }
}
