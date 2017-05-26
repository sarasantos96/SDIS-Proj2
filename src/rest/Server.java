package rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;
import db.*;
import logic.Message;
import logic.Task;
import logic.User;
import org.json.JSONException;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class Server {
    private final int PORT_NUMBER = 8000;
    private final String CONTEXT = "/application/app";
    private HttpServer httpServer;
    private DBConnection dbc;

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
                break;
            case "addToDo":
                boolean addTodoSuccess = dbc.addToDo(request.getTodo_text(),request.getIdGroup());
                r = new JSONResponse(addTodoSuccess);
                r.addToDoResponse();
                response = r.toString();
                break;
            case "checkToDo":
                boolean checkToDoSuccess = dbc.checkToDo(request.getIdTodo());
                r = new JSONResponse(checkToDoSuccess);
                r.checkToDoResponse();
                response = r.toString();
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
            System.out.println(idUser);
            ArrayList<User> getUsers = dbc.getGroupUsers(idUser);
            System.out.println(getUsers.toArray());
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

    public Server() throws IOException{
        //Initialize HttpServer
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT_NUMBER),0);
        httpServer.createContext(CONTEXT, new MyHandler());
        httpServer.setExecutor(null);
        httpServer.start();
        DBCreator.createDataBase();
        dbc = new DBConnection();

        System.out.println("Server running...");
    }

    public static void main(String [] args) throws IOException{
        Server appServer = new Server();
    }

}
