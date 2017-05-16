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

    public void signInRequest(String name, String username, String password)throws JSONException, IOException{
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("username",username);
        obj.put("password",password);

        JSONObject signInObj = new JSONObject();
        signInObj.put("signIn",obj);

        String signInJson = signInObj.toString();
        System.out.println(signInJson);
        sendPOSTMessage(signInJson);
    }

    public void createGroupRequest(String groupName)throws JSONException, IOException{
        JSONObject obj = new JSONObject();
        obj.put("name", groupName);

        JSONObject createGroupObj = new JSONObject();
        createGroupObj.put("createGroup",obj);

        String createGroupJson = createGroupObj.toString();
        System.out.println(createGroupJson);
        sendPOSTMessage(createGroupJson);
    }

    public void joinGroupRequest(int idGroup, int idUser)throws JSONException, IOException{
        JSONObject obj = new JSONObject();
        obj.put("idGroup", idGroup);
        obj.put("idUser", idUser);

        JSONObject joinGroupObj = new JSONObject();
        joinGroupObj.put("joinGroup",obj);

        String joinGroupJson = joinGroupObj.toString();
        System.out.println(joinGroupJson);
        sendPOSTMessage(joinGroupJson);
    }

    public void sendMessageRequest(int idGroup, int idUser, String text)throws JSONException, IOException{
        JSONObject obj = new JSONObject();
        obj.put("idGroup", idGroup);
        obj.put("idUser", idUser);
        obj.put("text",text);

        JSONObject sendMessageObj = new JSONObject();
        sendMessageObj.put("sendMessage",obj);

        String sendMessageJson = sendMessageObj.toString();
        System.out.println(sendMessageJson);
        sendPOSTMessage(sendMessageJson);
    }

    public void addToDoRequest(int idGroup, String text)throws JSONException, IOException{
        JSONObject obj = new JSONObject();
        obj.put("idGroup", idGroup);
        obj.put("text",text);

        JSONObject addToDoObj = new JSONObject();
        addToDoObj.put("addToDo",obj);

        String addToDoJson = addToDoObj.toString();
        System.out.println(addToDoJson);
        sendPOSTMessage(addToDoJson);
    }

    public void checkToDoRequest(int idToDo)throws JSONException, IOException{
        JSONObject obj = new JSONObject();
        obj.put("idToDo", idToDo);

        JSONObject checkToDoObj = new JSONObject();
        checkToDoObj.put("checkToDo",obj);

        String checkToDoJson = checkToDoObj.toString();
        System.out.println(checkToDoJson);
        sendPOSTMessage(checkToDoJson);
    }

    public static void main(String [] args) throws IOException, URISyntaxException, JSONException{

        Client client = new Client();
        //client.sendGETMessage();
        client.signInRequest("Joao Silva","joaosilva123","sdjfhfdcsdf" );
        client.logInRequest("joaosilva123","sdjfhfdcsdf");
        client.createGroupRequest("SDIS");
        client.joinGroupRequest(1,3);
        client.sendMessageRequest(1,3,"Hello!");
        client.addToDoRequest(1,"Finish REST");
        client.checkToDoRequest(1);

    }

}
