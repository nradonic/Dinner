package Table;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by NickRadonic on 11/20/14.
 */
public class Chopstick {
    private final String chopstickNumber;
    private  String Owner;
    private final String none = "None";

    Chopstick(String chopstickNumber){
        this.chopstickNumber = chopstickNumber;
        Owner = none;
    }

    public String getOwner(){
        return Owner;
    }

    public void setOwner(String Owner){
        this.Owner = Owner;
    }

    public boolean getChopstickOwnership(String philosopher){
        synchronized (Owner){
            if (Owner.equals(none)){
                Owner = philosopher;
            }
        }
        if(Owner.equals(philosopher)) {
            //printThread(chopstickNumber+ " picked up by " + philosopher );
            return true;
        }
        return false;
    }

    public void releaseChopstick(){
        String philosopher = Owner;
        synchronized (Owner){
            Owner = none;
        }
        //printThread("Chopstick "+chopstickNumber+ " released by " + philosopher );
    }
    private void printThread(String message){
        synchronized (System.out) {
            System.out.println(message);
            System.out.flush();
        }
    }
}
