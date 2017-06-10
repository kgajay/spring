package com.spring3.demo.java8;

/**
 * @author ajay.kg created on 10/06/17.
 */
public interface Vehicle {

    default void print(){
        System.out.println("I am a vehicle!");
    }

    static void blowHorn(){
        System.out.println("Blowing horn!!!");
    }

}
