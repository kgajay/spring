package com.spring3.demo.java8;

/**
 * @author ajay.kg created on 10/06/17.
 */
public interface FourWheeler {

    default void print(){
        System.out.println("I am a four wheeler!");
    }
}
