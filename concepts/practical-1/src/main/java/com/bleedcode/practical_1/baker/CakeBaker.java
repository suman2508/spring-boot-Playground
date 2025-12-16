package com.bleedcode.practical_1.baker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bleedcode.practical_1.frosting.Frosting;
import com.bleedcode.practical_1.syrup.Syrup;

import lombok.RequiredArgsConstructor;

@Service
// @RequiredArgsConstructor // Uncomment this annotation to enable constructor injection
public class CakeBaker {

    private final Syrup syrup;
    private final Frosting frosting;

    //User-defined constructor for dependency injection
    //Use Qualifier as needed to resolve ambiguity
    /*
       Without @Qulifier, Spring will throw an exception if there are multiple beans of the same type.
       NoUniqueBeanDefinitionException: No qualifying bean of type 'com.bleedcode.practical_1.services.Syrup' available: expected single matching bean but found 2: chocolateSyrup,strawberrySyrup
     */
    public CakeBaker(
       // @Qualifier("chocolateSyrup") Syrup syrup, 
       // @Qualifier("strawberryFrosting") Frosting frosting){
        Syrup mysyrup, 
        Frosting myfrosting){
        this.syrup = mysyrup;
        this.frosting = myfrosting;
    }

    public void bakeCake(){
        System.out.println("Baking cake with:");
        System.out.println("- Syrup: " + syrup.getSyrupType());
        System.out.println("-Frosting: " + frosting.getFrostingType());
    }

}
