package rest;

import com.sun.net.httpserver.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;


import javax.net.ssl.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by catarina on 17-05-2017.
 */
public class ServerTest {
    private static Integer port = 8000;

    public ServerTest() throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, UnrecoverableKeyException, KeyManagementException {

        //configure SSL
        char[] password = "123456".toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");

        File keyFile = new File("./src/rest/server.keys");
        ks.load(new FileInputStream(keyFile), password);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, password);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        HttpsServer server = HttpsServer.create(new InetSocketAddress(port), 0);

        server.setHttpsConfigurator (new HttpsConfigurator(sslContext){

            public void configure(HttpsParameters params){

                SSLContext c = getSSLContext();
                SSLParameters sslparams = c.getDefaultSSLParameters();
                params.setSSLParameters(sslparams);
            }
        });

        /*HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/application/app", new Handler());*/

        server.createContext("/application/app", new Handler());

        server.setExecutor(null);
        server.start();

        System.out.println("server is starting...");
    }

    public String handleGetRequest(HttpExchange exchange){

        URI uri = exchange.getRequestURI();
        String uri_string = uri.toString();

        System.out.println(uri_string);

        String response = "server successfully received a get request";

        return response;
    }

    class Handler implements HttpHandler {

        public void handle(HttpExchange exchange) throws IOException{

            String method = exchange.getRequestMethod();
            System.out.println("Received Request: " + method);

            String response = new String();

            if(method.equals("GET")){
                response = handleGetRequest(exchange);
            }

            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, KeyManagementException {
        ServerTest server = new ServerTest();
    }
}

/*
HttpServer:
https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html

HttpsServer:
https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpsServer.html

Another httpsServer:
https://docs.oracle.com/javase/7/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpsServer.html

Como fazer ligação http e https:
https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html

HttpsConfigurator:
https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpsConfigurator.html

https://stackoverflow.com/questions/2308479/simple-java-https-server

*/