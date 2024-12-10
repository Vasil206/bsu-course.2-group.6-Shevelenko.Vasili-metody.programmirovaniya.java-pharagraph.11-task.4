
package Client;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client implements Runnable {

    private final Semaphore callCenter;
    
    public Client(Semaphore callCenter){

        this.callCenter = callCenter;
    }
    
    @Override
    public void run(){
        
        var rand = new Random();
        boolean isGetCalling;

        System.out.println(super.getName() + " is waiting...");                         //ERROR

        try { 
            isGetCalling = callCenter.tryAcquire(rand.nextInt(5), TimeUnit.SECONDS);
            
            if (isGetCalling){
                
                System.out.println(super.getName() + " is speaking...");                //ERROR
            
                Thread.currentThread().sleep(rand.nextInt(5000));
                
                callCenter.release();
            }
            
            System.out.println(super.getName() + " called down.");                      //ERROR
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
