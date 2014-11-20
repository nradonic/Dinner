package Table;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by NickRadonic on 11/20/14.
 */
public class Dinner extends JFrame{
    private ArrayList<Chopstick> chopsticks = new ArrayList<>();
    private ArrayList<Thread> philosophers = new ArrayList<>();
    private final int PhilosopherCount = 15;
    private ArrayList<JPanel> jPanels = new ArrayList<>();
    private final int displayBox = 700;

    Dinner(){
        createChopsticks();
        createJPanels();
        createPhilosophers();
    }

    private void createChopsticks() {
        for (int index = 0; index < PhilosopherCount; index++) {
            Chopstick c = new Chopstick("Chopstick " + (index + 1) % 5 + " - " + index);
            c.setOwner("None");
            chopsticks.add(c);
        }
    }

    private void createPhilosophers(){

        for(int index = 0; index < PhilosopherCount; index++){
            int leftCSNumber = index;
            int rightCSNumber = (index+PhilosopherCount-1)%PhilosopherCount;
            Thread t = new Thread(new Philosopher("Philosopher "+index, chopsticks.get(leftCSNumber), chopsticks.get(rightCSNumber), jPanels.get(index)));
            philosophers.add(t);
        }
        for(int index=0; index<PhilosopherCount; index++){
            philosophers.get(index).start();
        }
    }
private void createJPanels(){

    setTitle("Dinner With Philosophers");
    setSize(displayBox, displayBox);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    JPanel jpf = new JPanel();
    jpf.setLayout(null);
    jpf.setBackground(Color.yellow);
    jpf.setPreferredSize(new Dimension(displayBox,displayBox));
    add(jpf);

    for(int index=0; index<PhilosopherCount; index++){
        JPanel jp = new JPanel();
        jp.setLayout(null);
        jp.setPreferredSize(new Dimension(displayBox,displayBox));
        jp.setBounds(getJPBoundsX(index), getJPBoundsY(index), displayBox/PhilosopherCount,displayBox/PhilosopherCount);
        jPanels.add(jp);
        jpf.add(jp);
    }
    setVisible(true);
}

    private int getJPBoundsY(int index){

        double temp = Math.cos((3/4 - index/(double)PhilosopherCount) * 2 * Math.PI ) * (displayBox/2-displayBox/PhilosopherCount) + displayBox/2 - displayBox/PhilosopherCount/2;
        return (int)Math.floor(temp);
    }
    private int getJPBoundsX(int index){
        double temp = Math.sin((3/4 - index/(double)PhilosopherCount) * 2 * Math.PI ) * (displayBox/2-displayBox/PhilosopherCount) + displayBox/2 - displayBox/PhilosopherCount/2;
        return (int)Math.floor(temp);
    }
    public static void main(String[] args){
        Dinner dinner = new Dinner();
    }
}
