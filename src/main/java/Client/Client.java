
package Client;

import java.util.concurrent.Semaphore;

public class Client implements Runnable {

    private Boolean canceletionTocken = false;
    private Thread calling;
    
    public Client(){
    }

    public void callUp(Semaphore callCenter){

        if(callCenter == null){

            throw new IllegalArgumentException("client cannot call to nothing");
        }

        var callingInner = new Calling(this, callCenter);

        calling = new Thread(callingInner);
        calling.start();
    }

    public void callDown(){

        if(calling != null){

            calling.interrupt();
            calling = null;
        }
    }

    public void speak(long milliseconds){

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            
            e.printStackTrace();
        }
    }


    public void stop(){
        canceletionTocken = true;
    }
    
    @Override
    public void run(){
        
        try{

            while(!canceletionTocken){
                
                //work load, while waiting
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }


    protected class Calling implements Runnable {

        private Semaphore callCenter;
        private Client client;

        public Calling(Client client, Semaphore callCenter){

            if(client == null || callCenter == null){

                throw new IllegalArgumentException("bad arguments in calling");
            }

            this.client = client;
            this.callCenter = callCenter;
        }

        @Override
        public void run() {
            
            try {
                callCenter.acquire();

                client.speak(3000);
                client.stop();
                callCenter.release();
            } catch (InterruptedException e) {
                
            }
        }
    
        
    }

}