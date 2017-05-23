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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

/**
 * Created by catarina on 17-05-2017.
 */
public class ClientTest {

    public static void main(String[] args) throws Exception {

        // Users should use JKS for storing trust anchors and PKCS12 for private keys.

        char[] password = "123456".toCharArray();
        KeyStore trustStore = KeyStore.getInstance("JKS");

        File keyFile = new File("./src/rest/truststore");
        FileInputStream instream = new FileInputStream(keyFile);
        try{
            trustStore.load(instream, password);
        }
        finally{
            instream.close();
        }

        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                new String[] {"TLSv1"},
                null,
                NoopHostnameVerifier.INSTANCE
        );

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", sslsf)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        try {

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
        } finally {
            httpClient.close();
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