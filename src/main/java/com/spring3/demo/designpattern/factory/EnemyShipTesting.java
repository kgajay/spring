package com.spring3.demo.designpattern.factory;

import java.util.Scanner;

/**
 * @author ajay.kg created on 25/06/17.
 */
public class EnemyShipTesting {

    public static void main(String[] args) {

        EnemyShip ufoShip = new UFOEnemyShip();
        // doStuffEnemy(ufoShip);

        Scanner userInput = new Scanner(System.in);
        String enemyShipOption = "";

        EnemyShipFactory enemyShipFactory = new EnemyShipFactory();
        EnemyShip theEnemy = null;
        System.out.println("What type of ship? (U / R)");
        if (userInput.hasNextLine()) {
            String typeOfShip = userInput.nextLine();
            theEnemy = enemyShipFactory.makeEnemyShip(typeOfShip);
        }
        if (theEnemy != null) {
            doStuffEnemy(theEnemy);
        }
    }


    public static void doStuffEnemy(EnemyShip anEnemyShip){
        anEnemyShip.displayEnemyShip();
        anEnemyShip.followHeroShip();
        anEnemyShip.enemyShipShoots();
    }
}
