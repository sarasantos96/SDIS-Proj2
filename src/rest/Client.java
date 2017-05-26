package rest;

import logic.Message;
import logic.Task;
import logic.User;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import tcp.TCPClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class Client {
    private static final String HOST = "localhost";
    private static final String PORT_NUMBER = "8000";
    private final int TCP_PORT_NUMBER = 8001;
    private static final String PATH = "application/app";
    private static String uri;
    private static CloseableHttpClient httpClient;
    public static User logUser;
    private TCPClient tcp_client;

    public Client(){
        uri = "http://" + HOST +":" + PORT_NUMBER + "/" + PATH;
        httpClient = HttpClients.createDefault();
        logUser = null;
        this.tcp_client = new TCPClient(HOST, TCP_PORT_NUMBER);
    }


    public static List sendGETMessage(String name, String value) throws URISyntaxException, IOException, JSONException{
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(HOST+":"+PORT_NUMBER).setPath(PATH);
        URI uribuilder = builder.build();
        HttpGet httpget = new HttpGet(uribuilder);
        httpget.addHeader(name,value);

        System.out.println("Executing " + httpget.getRequestLine());
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpClient.execute(httpget, responseHandler);
        JSONResponse jsonResponse = new JSONResponse(responseBody);
        jsonResponse.parseJSONResponse();

        if(jsonResponse.getType().equals("getMessagesGroup"))
            return jsonResponse.getMessages();
        else if(jsonResponse.getType().equals("getTodoGroup")) {
            return jsonResponse.getTasks();
        }else{
            return jsonResponse.getUsers();
        }

    }

    public static boolean sendPOSTMessage(String requestBody) throws IOException, JSONException{
        HttpPost httpPost = new HttpPost(uri);
        HttpEntity entity = new ByteArrayEntity(requestBody.getBytes());
        httpPost.setEntity(entity);

        System.out.println("Executing " + httpPost.getRequestLine());
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpClient.execute(httpPost, responseHandler);
        JSONResponse response = new JSONResponse(responseBody);
        response.parseJSONResponse();
        if(response.getType().equals("login")){
            if(response.isSuccess())
                logUser = response.getLogUser();
        }
        return response.isSuccess();
    }



    public static void main(String [] args) throws IOException, URISyntaxException, JSONException{

        Client client = new Client();

        //JSONRequest request = new JSONRequest("signIn","joaosilva","joao","1234","","","","","","");
        //client.sendPOSTMessage(request.getRequest());
        String requestName = "getUsers";
        String value = "";

        List<User> t = client.sendGETMessage(requestName,value);
        System.out.println( t.size());
        for(User task :t){
            System.out.println(task.getUsername());
        }
        /*JSONRequest jsonRequest = new JSONRequest("checkToDo","","","","", "","","5", "", "");
        sendPOSTMessage(jsonRequest.getRequest());*/
    }

}
