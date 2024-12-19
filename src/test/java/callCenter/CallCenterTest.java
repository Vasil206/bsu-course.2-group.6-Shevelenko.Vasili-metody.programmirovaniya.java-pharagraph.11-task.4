
package callCenter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Client.ClientTest;

public class CallCenterTest {
    
    @Test
    @DisplayName("callcenter task")
    public void callCenterTest() throws InterruptedException{

        var startStack = new ArrayList<>();
        var stopStack = new ArrayList<>();

        var callCenter = new Semaphore(2, true);
        var clients = new ArrayList<ClientTest>();

        for(int i = 0; i < 5; i++){

            var client = new ClientTest(i);
            clients.add(client);
        }

        try{
            //all clients are calling
            clients.get(1).callUp(callCenter);
            startStack.add(1);

            Thread.sleep(1000);
            clients.get(2).callUp(callCenter);
            startStack.add(2);

            Thread.sleep(10);
            clients.get(4).callUp(callCenter);
            startStack.add(4);

            Thread.sleep(10);
            clients.get(0).callUp(callCenter);
            startStack.add(0);

            Thread.sleep(10);
            clients.get(3).callUp(callCenter);
            startStack.add(3);

            //order of client's ending speaking, client 0 called down
            stopStack.add(1);
            stopStack.add(0);
            stopStack.add(2);
            stopStack.add(4);
            stopStack.add(3);


            //client number 0 cannot wait
            Thread.sleep(2000);
            clients.get(0).callDown();


            //client number 0 calls again
            Thread.sleep(2300);
            clients.get(0).callUp(callCenter);
            startStack.add(0);

            //client 0 ending the speaking
            stopStack.add(0);

            ClientTest.allClientsHaveSpoken.await();
        } catch(Exception ex) {

            ex.printStackTrace();
        }
        
        assertThat(ClientTest.startCallingStack).isEqualTo(startStack);
        assertThat(ClientTest.stopCallingStack).isEqualTo(stopStack);

    }
}
