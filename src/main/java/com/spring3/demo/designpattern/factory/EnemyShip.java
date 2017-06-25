package com.spring3.demo.designpattern.factory;

import lombok.Data;

/**
 * @author ajay.kg created on 25/06/17.
 */
@Data
public abstract class EnemyShip {

    private String name;
    private double amtDamage;


    public void followHeroShip() {
        System.out.println(getName() + " is following the hero");
    }

    public void displayEnemyShip() {
        System.out.println(getName() + " is on the screen");
    }

    public void enemyShipShoots() {
        System.out.println(getName() + " attacks and does " + getAmtDamage());
    }

}