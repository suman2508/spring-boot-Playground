package com.bleedcode.practical_1.syrup;

import org.springframework.stereotype.Component;

// @Component
public class StrawberrySyrup implements Syrup{

    @Override
    public String getSyrupType(){
        return "Strawberry Syrup";
    }
}
