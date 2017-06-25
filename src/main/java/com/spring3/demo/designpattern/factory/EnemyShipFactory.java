package com.spring3.demo.designpattern.factory;

/**
 * @author ajay.kg created on 25/06/17.
 */
public class EnemyShipFactory {

    public EnemyShip makeEnemyShip(String newShipType) {
        EnemyShip enemyShip = null;
        switch (newShipType){
        case "U":
            return new UFOEnemyShip();
        case "R":
            return new RocketEnemyShip();
        case "B":
            return new BigUFOEnemyShip();
        }
        return null;
    }
}
