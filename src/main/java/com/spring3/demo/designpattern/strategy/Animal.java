package com.spring3.demo.designpattern.strategy;

import lombok.Data;

/**
 * @author ajay.kg created on 25/06/17.
 */
@Data
public class Animal {

    private String name;
    private double height;
    private int weight;
    private String favFood;
    private double speed;
    private String sound;

    public Flys flyingType;

    /* BAD
    * You don't want to add methods to the super class.
    * You need to separate what is different between subclasses
    * and the super class

    public void fly(){

        System.out.println("I'm flying");

    }
    */

    // Animal pushes off the responsibility for flying to flyingType

    public String tryToFly() {

        return flyingType.fly();

    }

    // If you want to be able to change the flyingType dynamically
    // add the following method

    public void setFlyingAbility(Flys newFlyType) {

        flyingType = newFlyType;
    }

}