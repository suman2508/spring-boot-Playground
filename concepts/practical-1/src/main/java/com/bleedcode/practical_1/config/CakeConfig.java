package com.bleedcode.practical_1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bleedcode.practical_1.frosting.ChocolateFrosting;
import com.bleedcode.practical_1.frosting.Frosting;
import com.bleedcode.practical_1.frosting.StrawberryFrosting;
import com.bleedcode.practical_1.syrup.ChocolateSyrup;
import com.bleedcode.practical_1.syrup.StrawberrySyrup;
import com.bleedcode.practical_1.syrup.Syrup;

@Configuration
public class CakeConfig {

    @Value("${cake.frosting:strawberry}")
    private String frostingType;

    @Value("${cake.syrup:strawberry}")
    private String sryupType;

    @Bean
    public Frosting frosting() {
        if("chocolate".equalsIgnoreCase(frostingType)){
            return new ChocolateFrosting();
        }

        return new StrawberryFrosting();
    }

    @Bean
    public Syrup syrup() {
        if("chocolate".equalsIgnoreCase(sryupType)){
            return new ChocolateSyrup();
        }

        return new StrawberrySyrup();
    }   

}
