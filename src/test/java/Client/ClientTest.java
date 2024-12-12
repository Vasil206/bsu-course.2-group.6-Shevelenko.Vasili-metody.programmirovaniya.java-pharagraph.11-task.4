package Client;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class ClientTest extends Client {

    public static ArrayList<Integer> startCallingStack = new ArrayList<>();
    public static ArrayList<Integer> stopCallingStack = new ArrayList<>();
    
    public static CountDownLatch allClientsHaveSpoken = new CountDownLatch(5);

    private Integer name;

    public ClientTest(Integer name){

        this.name = name;
    }

    @Override
    public void callUp(Semaphore callCenter) {

        startCallingStack.add(name);
        super.callUp(callCenter);
    }
    
    @Override
    public void callDown() {
        
        stopCallingStack.add(name);
        super.callDown();
    }

    @Override
    public void stop() {
        
        stopCallingStack.add(name);
        allClientsHaveSpoken.countDown();
        super.stop();
    }


}
