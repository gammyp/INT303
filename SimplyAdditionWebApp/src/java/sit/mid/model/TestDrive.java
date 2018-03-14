/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.mid.model;

import java.util.Random;

/**
 *
 * @author Game
 */
public class TestDrive {
    public static void main(String[] args) {
        AdditionQuestion aq = new AdditionQuestion();
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            System.out.printf("Score %d of %d \n",aq.getCorrect(),aq.getTotal());
            System.out.println(aq.nextQuestion());
            int x = r.nextInt(18);
            System.out.printf("Your answer: %d --- Correct Answer: %d \n",x,aq.getX()+aq.getY());
            if (aq.checkAnswer(x)) {
                aq.incrementCorrect();
            }
            System.out.println("----------------------------------");
        }
        System.out.printf("Summary score: %d of %d \n",aq.getCorrect(),aq.getTotal());
    }
}
