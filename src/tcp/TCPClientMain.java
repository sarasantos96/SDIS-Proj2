package tcp;

/**
 * Created by dalugoga on 22-05-2017.
 */
public class TCPClientMain {
    public static void main(String args[]) {
        TCPClient client = new TCPClient("127.0.0.1", 8001);

        client.receiveSocketMessage();
    }
}
