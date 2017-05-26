package tcp;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 * Created by dalugoga on 22-05-2017.
 */
public class TCPServer {
    private int port_number;
    private ServerSocket server_socket;


    public TCPServer(int port_number) {
        this.port_number = port_number;
        openSockets();
    }

    public void openSockets(){
        try {
            server_socket = new ServerSocket(this.port_number);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            try {
                Socket socket = server_socket.accept();
                ServiceRequest service = new ServiceRequest(socket);
                Thread t = new Thread(service);
                t.start();
                System.out.println("just accepted!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    class ServiceRequest implements Runnable {
        private Socket socket;
        private PrintWriter out;

        public void sendTCPMessage(String message) {
            out.println(message);
        }

        public ServiceRequest(Socket connection) {
            this.socket = connection;
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            int i = 0;
            while(i < 10){
                sendTCPMessage("" + i);
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
