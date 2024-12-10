
package task4.ph11varatask4;

import Client.Client;
import java.util.concurrent.Semaphore;

public class Ph11VarATask4 {

    public static void main(String[] args) {
        
        /*
         * 
        */
        Semaphore callCenter = new Semaphore(3, true);
        
        for(int i = 0; i < 10; i++){
            
            new Client(callCenter).start();
        }
    }
}
