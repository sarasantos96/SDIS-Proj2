package turboWork;

import gui.StartBox;
import rest.Client;


public class Proj {
    private Client client;
    private StartBox startgui;

    public static void main(String[] args){
        Proj p = new Proj();
        try{
            p.client = new Client(p);
            p.client.init();
            p.startgui = new StartBox();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateUsers(){
        if(this.startgui.isViewHomeBox())
            this.startgui.updateHomeBoxParticipants();
    }

    public void updateTasks(){
        if(this.startgui.isViewHomeBox())
            this.startgui.updateHomeBoxTasks();
    }

    public void updateMessages(){
        if(this.startgui.isViewHomeBox())
            this.startgui.updateHomeBoxMessages();
    }
}
