package rest;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final String HOST = "localhost";
    private final String PORT_NUMBER = "8000";
    private final String PATH = "application/app";
    private String uri;
    private CloseableHttpClient httpClient;

    public Client(){
        uri = "http://" + HOST +":" + PORT_NUMBER + "/" + PATH;
        httpClient = HttpClients.createDefault();
    }


    public void sendGETMessage() throws URISyntaxException, IOException{
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(HOST+":"+PORT_NUMBER).setPath(PATH);
        URI uribuilder = builder.build();
        HttpGet httpget = new HttpGet(uribuilder);

        System.out.println("Executing " + httpget.getRequestLine());
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpClient.execute(httpget, responseHandler);
        System.out.println(responseBody);
    }

    public void sendPOSTMessage(String requestBody) throws IOException{
        HttpPost httpPost = new HttpPost(uri);
        HttpEntity entity = new ByteArrayEntity(requestBody.getBytes());
        httpPost.setEntity(entity);

        System.out.println("Executing " + httpPost.getRequestLine());
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpClient.execute(httpPost, responseHandler);
        System.out.println(responseBody);
    }

    public void logInRequest(String username, String password) throws JSONException, IOException{
        JSONObject obj = new JSONObject();
        obj.put("username",username);
        obj.put("password",password);

        JSONObject logInObj = new JSONObject();
        logInObj.put("login",obj);

        String loginJson = logInObj.toString();
        System.out.println(loginJson);
        sendPOSTMessage(loginJson);
    }

    public static void main(String [] args) throws IOException, URISyntaxException, JSONException{

        Client client = new Client();
        //client.sendGETMessage();
        client.logInRequest("joaosilva123","sdjfhfdcsdf");

    }

}
