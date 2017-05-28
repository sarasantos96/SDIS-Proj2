package rest;

import logic.Message;
import logic.Task;
import logic.User;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import tcp.TCPClient;
import turboWork.Proj;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStore;
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

    public Client(Proj proj) {
        this.tcp_client = new TCPClient(HOST, TCP_PORT_NUMBER, proj);
    }

    public void init(){
        uri = "https://" + HOST +":" + PORT_NUMBER + "/" + PATH;
        logUser = null;

        try {

            char[] password = "123456".toCharArray();

            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore ts = KeyStore.getInstance("JKS");

            File keyFile = new File("./src/certificates/client.keys");
            ks.load(new FileInputStream(keyFile), password);

            File trustStoreFile = new File("./src/certificates/truststore");
            ts.load(new FileInputStream(trustStoreFile), password);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    NoopHostnameVerifier.INSTANCE
            );

            httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public static List sendGETMessage(String name, String value) throws URISyntaxException, IOException, JSONException{

        HttpGet httpget = new HttpGet(uri);
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
}