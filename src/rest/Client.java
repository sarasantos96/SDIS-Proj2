package rest;

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
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;



public class Client {
    private static final String HOST = "localhost";
    private static final String PORT_NUMBER = "8000";
    private static final String PATH = "application/app";
    private static String uri;
    private static CloseableHttpClient httpClient;

    public Client(){
        uri = "http://" + HOST +":" + PORT_NUMBER + "/" + PATH;
        httpClient = HttpClients.createDefault();
    }


    public static void sendGETMessage() throws URISyntaxException, IOException{
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(HOST+":"+PORT_NUMBER).setPath(PATH);
        URI uribuilder = builder.build();
        HttpGet httpget = new HttpGet(uribuilder);

        System.out.println("Executing " + httpget.getRequestLine());
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpClient.execute(httpget, responseHandler);
        System.out.println(responseBody);
    }

    public static void sendPOSTMessage(String requestBody) throws IOException{
        HttpPost httpPost = new HttpPost(uri);
        HttpEntity entity = new ByteArrayEntity(requestBody.getBytes());
        httpPost.setEntity(entity);

        System.out.println("Executing " + httpPost.getRequestLine());
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpClient.execute(httpPost, responseHandler);
        System.out.println(responseBody);
    }


    public static void main(String [] args) throws IOException, URISyntaxException, JSONException{

        Client client = new Client();
        //client.sendGETMessage();
        /*client.signInRequest("Joao Silva","joaosilva123","sdjfhfdcsdf" );
        client.logInRequest("joaosilva123","sdjfhfdcsdf");
        client.createGroupRequest("SDIS");
        client.joinGroupRequest(1,3);
        client.sendMessageRequest(1,3,"Hello!");
        client.addToDoRequest(1,"Finish REST");
        client.checkToDoRequest(1);*/
        JSONRequest request = new JSONRequest("signIn","joaosilva","joao","1234","","","","","","");
        client.sendPOSTMessage(request.getRequest());
    }

}
