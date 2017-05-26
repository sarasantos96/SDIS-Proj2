package rest;


import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * Created by catarina on 17-05-2017.
 */
public class ClientTest {

    public static void main(String[] args) throws Exception {

        // Users should use JKS for storing trust anchors and PKCS12 for private keys.

        try {

            char[] password = "123456".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore ts = KeyStore.getInstance("JKS");

            File keyFile = new File("./src/rest/client.keys");
            ks.load(new FileInputStream(keyFile), password);

            File trustStoreFile = new File("./src/rest/truststore");
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

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    //.register("http", new PlainConnectionSocketFactory())
                    .register("https", sslsf)
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();

            HttpGet httpget = new HttpGet("https://localhost:8000/application/app");

            System.out.println("executing request: " + httpget.getRequestLine());

            CloseableHttpResponse response = httpClient.execute(httpget);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity entity1 = response.getEntity();
                EntityUtils.consume(entity1);
            } finally {
                response.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
/*
        CloseableHttpClient httpClient = HttpClients.createDefault();

        Content content = Request.Get("http://localhost:8000/application/app")
                .execute().returnContent();

        System.out.println("Content: " + content);*/

        /*Response response = (Response) Request.Get("http://localhost:8000/application/app")
                .execute().returnResponse();

        */

        /*HttpGet httpGet = new HttpGet("http://localhost:8000/application/app");

        CloseableHttpResponse response1 = httpClient.execute(httpGet);

        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }*/
    }
}

/*
HttpURLConnection:
http://download.java.net/jdk7/archive/b123/docs/api/java/net/HttpURLConnection.html

HttpsURLConnection:
http://download.java.net/jdk7/archive/b123/docs/api/javax/net/ssl/HttpsURLConnection.html

Apache:
https://hc.apache.org/httpcomponents-client-5.0.x/examples.html

Apache Example:
https://hc.apache.org/httpcomponents-client-ga/quickstart.html

Example:
http://stackoverflow.com/questions/10116961/can-you-explain-the-httpurlconnection-connection-process
*/