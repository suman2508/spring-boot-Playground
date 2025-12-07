package com.bleedcode.bean_concepts_demo.config;


import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bleedcode.bean_concepts_demo.components.PrototypeBean;
import com.bleedcode.bean_concepts_demo.components.SingletonBean;

@Component
public class ScopeTester {

    private final ApplicationContext context;

    public ScopeTester(ApplicationContext context) {
        this.context = context;
    }  

    public void testScopes() {

        System.out.println("\n---------Singleton Scope Test---------");
        SingletonBean singletonBean1 = context.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = context.getBean(SingletonBean.class);
        System.out.println("Singleton Bean 1: " + singletonBean1 );
        System.out.println("Singleton Bean 2: " + singletonBean2 );
        System.out.println("Singleton Beans are the same: " + (singletonBean1 == singletonBean2));

        System.out.println("\n---------Prototype Scope Test---------");
        Object prototypeBean1 = context.getBean(PrototypeBean.class);
        Object prototypeBean2 = context.getBean(PrototypeBean.class);
        System.out.println("Prototype Bean 1: " + prototypeBean1 );
        System.out.println("Prototype Bean 2: " + prototypeBean2 );
        System.out.println("Prototype Beans are the same: " + (prototypeBean1 == prototypeBean2));
    }

}
