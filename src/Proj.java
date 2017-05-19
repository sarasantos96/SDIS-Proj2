import gui.StartBox;
import rest.Client;
import rest.Server;


public class Proj {
    private Client client;
    private Server server;
    private StartBox startgui;

    public static void main(String[] args){
        Proj p = new Proj();
        try{
            p.client = new Client();
            p.startgui = new StartBox();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
