/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.mid.model;

/**
 *
 * @author Game
 */
public class AdditionQuestion {
    private int correct;
    private int total;
    private int x; //num 1
    private int y; // num 2
    
    private void genX() {
        x = (int)(Math.random()*10);
    }
    
    private void genY() {
        y = (int)(Math.random()*10);
    }
    
    public String nextQuestion() {
        genX();
        genY();
        total++;
        return String.format("What is %d + %d ?",x ,y);
    }
    
    public boolean checkAnswer(int answer) {
        return answer == x+y;
    }
    
    public void incrementCorrect() {
        correct++;
    }
    
    public int getCorrect() {
        return this.correct;
    }
    
    public int getTotal() {
        return this.total;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
}
