package rest;

import com.sun.net.httpserver.*;
import db.*;
import logic.Message;
import logic.Task;
import logic.User;
import org.json.JSONException;
import tcp.TCPServer;


import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;


public class Server {
    private final int PORT_NUMBER = 8000;
    private final int TCP_PORT_NUMBER = 8001;
    private final String CONTEXT = "/application/app";
    private DBConnection dbc;
    private TCPServer tcp_server;

    public Server() {
        this.tcp_server = new TCPServer(TCP_PORT_NUMBER);
    }

    public void init(){

        DBCreator.createDataBase();
        dbc = new DBConnection();

        try {
            //configure SSL
            char[] password = "123456".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore ts = KeyStore.getInstance("JKS");

            File keyFile = new File("./src/certificates/server.keys");
            ks.load(new FileInputStream(keyFile), password);

            File trustStoreFile = new File("./src/certificates/truststore");
            ts.load(new FileInputStream(trustStoreFile), password);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            HttpsServer server = HttpsServer.create(new InetSocketAddress(PORT_NUMBER), 0);

            server.setHttpsConfigurator (new HttpsConfigurator(sslContext){

                public void configure(HttpsParameters params){

                    SSLContext c = getSSLContext();
                    SSLParameters sslparams = c.getDefaultSSLParameters();
                    params.setSSLParameters(sslparams);
                }
            });

            server.createContext(CONTEXT, new MyHandler());

            server.setExecutor(null);
            server.start();

            System.out.println("server running ...");

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }



    public String handlePostRequest(HttpExchange exchange) throws IOException, JSONException {
        InputStream is = exchange.getRequestBody();
        byte[] bytes = new byte[250];
        is.read(bytes);
        String received = new String(bytes).trim();

        JSONRequest request = new JSONRequest(received);
        boolean parse = request.parseRequest();
        if(!parse){
            System.out.println("Bad Request");
            return "ERROR";
        }

        String response = new String();

        JSONResponse r;
        switch (request.getType()){
            case "login":
                boolean logInSuccess = dbc.verifyLogin(request.getUsername(),request.getPassword());
                r = new JSONResponse(logInSuccess);
                if(!logInSuccess){
                    r.logInResponse();
                }else{
                    User user = dbc.getUser(request.getUsername());
                    r.logInResponse(user);
                }
                response = r.toString();
                break;
            case "signIn":
                boolean signInSuccess = dbc.registerUser(request.getName(),request.getUsername(),request.getPassword());
                r = new JSONResponse(signInSuccess);
                r.signInResponse();
                response = r.toString();
                if(signInSuccess)
                    tcp_server.sendMessageToAllClients("REFRESH USERS");
                break;
            case "createGroup":
                boolean createGroupSuccess = dbc.createGroup(request.getGroupname());
                r = new JSONResponse(createGroupSuccess);
                r.createGroupResponse();
                response = r.toString();
                break;
            case "joinGroup":
                boolean joinGroupSuccess = dbc.joinGroup(request.getIdUser(),request.getIdGroup());
                r = new JSONResponse(joinGroupSuccess);
                r.joinGroupResponse();
                response = r.toString();
                break;
            case "sendMessage":
                boolean sendMessageSuccess = dbc.sendMessage(request.getMessage_text(),request.getIdUser(),request.getIdGroup());
                r = new JSONResponse(sendMessageSuccess);
                r.sendMessageResponse();
                response = r.toString();
                if(sendMessageSuccess)
                    tcp_server.sendMessageToAllClients("REFRESH MESSAGES");
                break;
            case "addToDo":
                boolean addTodoSuccess = dbc.addToDo(request.getTodo_text(),request.getIdGroup());
                r = new JSONResponse(addTodoSuccess);
                r.addToDoResponse();
                response = r.toString();
                if(addTodoSuccess)
                    tcp_server.sendMessageToAllClients("REFRESH TODO");
                break;
            case "checkToDo":
                boolean checkToDoSuccess = dbc.checkToDo(request.getIdTodo());
                r = new JSONResponse(checkToDoSuccess);
                r.checkToDoResponse();
                response = r.toString();
                if(checkToDoSuccess)
                    tcp_server.sendMessageToAllClients("REFRESH TODO");
                break;
        }
        return response;
    }

    public String handleGetRequest(HttpExchange exchange) throws JSONException {
        String response = new String();

        URI uri = exchange.getRequestURI();
        String uri_string = uri.toString();
        Headers headers = exchange.getRequestHeaders();

        if(headers.containsKey("getMessagesGroup")){
            List<String> keys = headers.get("getMessagesGroup");
            int idGroup = Integer.parseInt(keys.get(0));
            ArrayList<Message> getMessagesGroup = dbc.getGroupMessages(idGroup);
            JSONResponse responseTest = new JSONResponse(true);
            responseTest.getMessagesGroupResponse(getMessagesGroup);
            response = responseTest.toString();

        }else if(headers.containsKey("getTodoGroup")){
            List<String> keys = headers.get("getTodoGroup");
            int idGroup = Integer.parseInt(keys.get(0));

            ArrayList<Task> getGroupTasks = dbc.getGroupTasks(idGroup);
            JSONResponse responseTest = new JSONResponse(true);
            responseTest.getTodoGroup(getGroupTasks);
            response = responseTest.toString();
        }else if(headers.containsKey("getUsers")){
            List<String> keys = headers.get("getUsers");
            int idUser = Integer.parseInt(keys.get(0));
            ArrayList<User> getUsers = dbc.getGroupUsers(idUser);
            JSONResponse jsonResponse = new JSONResponse(true);
            jsonResponse.getUsersResponse(getUsers);
            response = jsonResponse.toString();
        }

        return response;
    }

    class MyHandler implements HttpHandler {
        public MyHandler(){}
        public void handle(HttpExchange exchange) throws IOException{
            String method = exchange.getRequestMethod();
            System.out.println("Received Request: " +method);

            String response = new String();

            if(method.equals("GET")){
                try{
                    response = handleGetRequest(exchange);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }else if(method.equals("POST")){
                try{
                    response = handlePostRequest(exchange);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
            exchange.sendResponseHeaders(200,response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());

            os.close();
        }
    }

    public static void main(String [] args) throws IOException{
        Server appServer = new Server();
        appServer.init();
    }

}
