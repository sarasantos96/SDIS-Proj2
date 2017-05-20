package logic;

import db.DBConnection;

import java.util.ArrayList;

/**
 * Created by dalugoga on 19-05-2017.
 */
public class Group {
    private final String name;
    private final int id;
    private ArrayList<User> members = new ArrayList<>();
    private ArrayList<Task> tasks = new ArrayList<>();

    public Group(String name, int id) {
        this.name = name;
        this.id = id;
        this.getUsers();
        this.getTasks();
    }

    private void getUsers(){
        DBConnection dbc = new DBConnection();
        members = dbc.getGroupUsers(this.id);
    }

    private void getTasks(){
        DBConnection dbc = new DBConnection();
        tasks = dbc.getGroupTasks(this.id);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String ret = new String("");
        ret = ret + "Group " + this.name + "\n";
        ret = ret + "Id: " + this.id + "\n";
        ret = ret + "Members:" + "\n";
        for(User u :this.members){
            ret = ret + "   " + u.getName() + "\n";
        }
        ret = ret + "Tasks:" + "\n";
        for(Task t:this.tasks){
            ret = ret + "   " + t.getTask() + "\n";
        }
        return ret;
    }

    public ArrayList<User> getMembers() {
        return members;
    }
}
