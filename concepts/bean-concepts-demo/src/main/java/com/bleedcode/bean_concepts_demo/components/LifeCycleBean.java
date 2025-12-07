package com.bleedcode.bean_concepts_demo.components;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class LifeCycleBean implements InitializingBean, DisposableBean {

    public LifeCycleBean() {
        System.out.println("LifeCycleBean: Constructor called");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("LifeCycleBean: @PostConstruct method called");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("LifeCycleBean: afterPropertiesSet called - Bean is initialized");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("LifeCycleBean: @PreDestroy method called");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("LifeCycleBean: destroy called - Bean is being destroyed");
    }

}
