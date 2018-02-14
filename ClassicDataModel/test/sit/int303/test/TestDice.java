/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.test;

import java.util.HashMap;
import java.util.Map;
import sit.int303.model.Dice;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author INT303
 */
public class TestDice {
    
    public TestDice() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void diceNotZero() {
        Dice dice = new Dice();
        int actual = dice.getFace();
        assertTrue("Face not equal 0", actual != 0);
    }
    
    @Test
    public void diceBetweenOneToSix() {
        Dice dice = new Dice();
        int actual = dice.getFace();
        assertTrue("Face should between 1 and 6", actual >= 1 && actual <= 6);
    }
    
    @Test
    public void diceBetweenOneAfterRoll() {
        Dice dice = new Dice();
        dice.roll();
        int actual = dice.getFace();
        assertTrue("Face should between 1 and 6", actual >= 1 && actual <= 6);
    }
    
    @Test
    public void allFaceOccerWhenRollNTime() {
        int n = 50;
        Map<Integer, Integer> result = rollDice(n);
        assertEquals(6, result.size());
    }
    
    private Map<Integer, Integer> rollDice(int n) {
        Map<Integer, Integer> map = new HashMap();
        Dice dice = new Dice();
        for (int i = 0; i < n; i++) {
            dice.roll();
            if(map.get(dice.getFace()) == null) {
                map.put(dice.getFace(), 1);
            }
            else {
                map.put(dice.getFace(), map.get(dice.getFace()) + 1);
            }
        }
        return map;
    }
}
