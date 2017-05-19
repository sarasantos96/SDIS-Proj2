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

    public Group(String name, int id) {
        this.name = name;
        this.id = id;
        this.getUsers();
    }

    private void getUsers(){
        DBConnection dbc = new DBConnection();
        members = dbc.getGroupUsers(this.id);
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getMembers() {
        return members;
    }
}
