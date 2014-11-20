package Table;

import com.sun.codemodel.internal.JClassContainer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by NickRadonic on 11/20/14.
 */
public class Philosopher implements Runnable{

    private String name;
    private enum PState{ thinking, eating, gettingCS};
    private PState philosopherState = PState.thinking;

    private Chopstick leftChopstick;
    private boolean leftChopstickOwnership = false;
    private Chopstick rightChopstick;
    private boolean rightChopstickOwnership = false;
    private JPanel jC;

    Philosopher(String name,  Chopstick leftChopstick, Chopstick rightChopstick, JPanel jC){
        this.name = name;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.jC = jC;
    }

    public void run() {
        // compute primes larger than minPrime
        while(true){

            switch(philosopherState){
                case thinking:{
                    synchronized (jC) {
                        jC.setBackground(Color.black);
                    }
                    try{Thread.sleep(100);}catch(Exception e){}
                    printThread(name + " is trying to get 2 chopsticks");
                    philosopherState = PState.gettingCS;

                    break;
                }
                case eating:{
                    synchronized (jC) {
                        jC.setBackground(Color.green);
                    }
                    try{Thread.sleep(100);}catch(Exception e){}
                    releaseChopsticks();
                    philosopherState = PState.thinking;
                    printThread("Philosopher " + name + " now thinking");

                    break;

                }
                case gettingCS:{
                   synchronized (jC) {
                       jC.setBackground(Color.red);
                   }
                    if(getChopsticks() == true) {
                        philosopherState = PState.eating;

                        printThread("                                    " + name + " is now eating");
                    }

                    break;
                }
            }

        }
    }

    private void releaseChopsticks(){
        leftChopstick.releaseChopstick();
        rightChopstick.releaseChopstick();
    }

    private boolean getChopsticks(){
        leftChopstickOwnership = leftChopstick.getChopstickOwnership(name);
        if (leftChopstickOwnership) {
            rightChopstickOwnership = rightChopstick.getChopstickOwnership(name);
            if (rightChopstickOwnership) {
                return true;
            }
            leftChopstick.releaseChopstick();
            return false;
        }
        return false;
//        rightChopstickOwnership = rightChopstick.getChopstickOwnership(name);
//        if(leftChopstickOwnership && rightChopstickOwnership){ return true;} else {
//        return false;}
    }

    private void printThread(String message){
        synchronized (System.out) {
            System.out.println(message);
            System.out.flush();
        }
    }
}
