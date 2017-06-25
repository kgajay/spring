package com.spring3.demo.designpattern.strategy;

/**
 * @author ajay.kg created on 25/06/17.
 */
public class Bird extends Animal {

    public Bird(){
        super();
        setSound("Tweet");
        flyingType = new ItFlys();
    }
}