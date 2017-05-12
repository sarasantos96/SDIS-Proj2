package rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class Server {
    private final int PORT_NUMBER = 8000;
    private final String CONTEXT = "/application/app";
    private HttpServer httpServer;

    public String handlePostRequest(HttpExchange exchange) throws IOException{
        String response = new String();

        InputStream is = exchange.getRequestBody();
        byte[] bytes = new byte[250];
        is.read(bytes);
        String received = new String(bytes).trim();
        System.out.println(received);

        response = "server successfully received a get request";

        return response;
    }

    public String handleGetRequest(HttpExchange exchange) {
        String response = new String();

        URI uri = exchange.getRequestURI();
        String uri_string = uri.toString();

        System.out.println(uri_string);

        response = "server successfully received a get request";

        return response;
    }

    class MyHandler implements HttpHandler{
        public MyHandler(){}
        public void handle(HttpExchange exchange) throws IOException{
            String method = exchange.getRequestMethod();
            System.out.println("Received Request: " +method);
            String response = new String();
            if(method.equals("GET")){
                response = handleGetRequest(exchange);
            }else if(method.equals("POST")){
                response = handlePostRequest(exchange);
            }

            exchange.sendResponseHeaders(200,response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public Server() throws IOException{
        //Initialize HttpServer
        httpServer = HttpServer.create(new InetSocketAddress(PORT_NUMBER),0);
        httpServer.createContext(CONTEXT, new MyHandler());
        httpServer.setExecutor(null);
        httpServer.start();
        System.out.println("Server running...");
    }

    public static void main(String [] args) throws IOException{
        Server appServer = new Server();
    }

}
