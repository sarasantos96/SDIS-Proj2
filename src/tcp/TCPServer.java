package tcp;

import java.net.*;
import java.util.*;
import java.io.*;

public class TCPServer {
    private int port_number;
    private ServerSocket server_socket;
    private ArrayList<ServiceRequest> service_requests;


    public TCPServer(int port_number) {
        this.port_number = port_number;
        this.service_requests = new ArrayList<>();
        openSockets();
        openThread();
        System.out.println("Server thread is open");
    }

    public void sendMessageToAllClients(String message){
        for(ServiceRequest service: service_requests){
            service.sendTCPMessage(message);
        }
    }

    public void openThread(){
        ServerTCPThread thread = new ServerTCPThread(this);
        Thread t = new Thread(thread);
        t.start();
    }

    public void openSockets(){
        try {
            server_socket = new ServerSocket(this.port_number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addService(ServiceRequest t){
        this.service_requests.add(t);
    }

    class ServerTCPThread implements Runnable{
        private TCPServer server;

        public TCPServer getServer(){
            return this.server;
        }

        public ServerTCPThread(TCPServer server){
            this.server = server;
        }

        public void run(){
            while(true) {
                try {
                    Socket socket = server.getServer_socket().accept();
                    ServiceRequest service = new ServiceRequest(socket, this);
                    Thread t = new Thread(service);
                    t.start();
                    System.out.println("just accepted!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ServiceRequest implements Runnable {
        private Socket socket;
        private PrintWriter out;

        public void sendTCPMessage(String message) {
            out.println(message);
        }

        public ServiceRequest(Socket connection, ServerTCPThread server_thread) {
            this.socket = connection;
            server_thread.getServer().addService(this);
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while(true){

            }
        }
    }

    public ServerSocket getServer_socket(){
        return this.server_socket;
    }
}
