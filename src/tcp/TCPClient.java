package tcp;
import turboWork.Proj;

import java.net.*;
import java.util.*;
import java.io.*;
/**
 * Created by dalugoga on 22-05-2017.
 */
public class TCPClient {
    private String host_name;
    private int port_number;
    private Socket socket;
    private BufferedReader in;
    private Proj turbo_work;

    public TCPClient(String host_name, int port_number, Proj proj) {
        this.host_name = host_name;
        this.port_number = port_number;
        this.turbo_work = proj;
        openSocket(host_name, port_number);
        openThread();
        System.out.println("Client thread is open");
    }

    public void openThread(){
        ClientTCPThread thread = new ClientTCPThread(this);
        Thread t = new Thread(thread);
        t.start();
    }

    public void openSocket(String host_name, int port_number){
        try {
            socket = new Socket(host_name, port_number);
            InputStreamReader insrt = new InputStreamReader(this.socket.getInputStream());
            in = new BufferedReader(insrt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean receiveSocketMessage(){
        try {
            String message;
            message = in.readLine();
            if(message == null) {
                return false;
            }

            System.out.println(message);

            switch(message.trim()){
                case "REFRESH USERS":
                    break;
                case "REFRESH MESSAGES":
                    break;
                case "REFRESH TODO":
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    class ClientTCPThread implements Runnable{
        private TCPClient client;
        public ClientTCPThread(TCPClient client){
            this.client = client;
        }

        public void run(){
            boolean open = true;
            while(open){
                open = client.receiveSocketMessage();
            }
        }
    }
}
