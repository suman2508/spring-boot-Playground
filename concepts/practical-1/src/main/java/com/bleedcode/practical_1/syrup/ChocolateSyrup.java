package com.bleedcode.practical_1.syrup;

import org.springframework.stereotype.Component;

// @Component
public class ChocolateSyrup implements Syrup{

    @Override
    public String getSyrupType() {
        return "Chocolate Syrup";
    }
}
