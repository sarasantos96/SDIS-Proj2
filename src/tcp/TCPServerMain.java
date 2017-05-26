package tcp;

/**
 * Created by dalugoga on 22-05-2017.
 */
public class TCPServerMain {
    public static void main(String args[]) {
        TCPServer server = new TCPServer(8001);
        while(true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            server.sendMessageToAllClients("olaaaaaaaaaaaaaa");
        }
    }
}
