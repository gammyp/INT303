/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.model;

/**
 *
 * @author INT303
 */
public class Dice {

    private int face;
    
    public Dice() {
        roll();
    }

    public int getFace() {
        return face;
    }

    public void roll() {
        this.face = (int)(Math.random()*6+1);
    }
    
}
