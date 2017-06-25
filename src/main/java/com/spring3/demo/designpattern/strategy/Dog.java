package com.spring3.demo.designpattern.strategy;

/**
 * @author ajay.kg created on 25/06/17.
 */
public class Dog extends Animal {

    public Dog() {
        super();
        setSound("Bark");
        flyingType = new CantFly();
    }

    public void digHole(){
        System.out.println("Dug a hole");
    }
}