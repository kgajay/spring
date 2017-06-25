package com.spring3.demo.designpattern.strategy;

/**
 * @author ajay.kg created on 25/06/17.
 */
public interface Flys {

    String fly();

}

class ItFlys implements Flys {

    @Override
    public String fly() {
        return "Flying High";
    }
}


class CantFly implements Flys {

    @Override
    public String fly() {
        return "Can not fly";
    }
}