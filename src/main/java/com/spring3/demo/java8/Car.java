package com.spring3.demo.java8;

/**
 * @author ajay.kg created on 10/06/17.
 */
public class Car implements Vehicle, FourWheeler {

    public void print(){
        Vehicle.super.print();
        FourWheeler.super.print();
        Vehicle.blowHorn();
        System.out.println("I am a car!");
    }


    public static void main(String args[]){
        Vehicle vehicle = new Car();
        vehicle.print();
    }
}
