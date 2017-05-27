package db;

import logic.*;
import java.sql.*;
import java.util.ArrayList;

public class DBConnection {
    private Connection conn;

    public DBConnection() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:./src/db/sdis2.db");
        }catch(SQLException e){
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }

    public ResultSet runSelect(String st){
        Statement stmt;
        ResultSet rs = null;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(st);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return rs;
    }

    public boolean runUpdate(String st){
        Statement stmt;
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate(st);
        }catch(SQLException e){
            e.printStackTrace(System.out);
            return false;
        }
        return true;
    }

    public boolean registerUser(String name, String username, String password){
        String values = "'" + name + "', " + "'" + username + "', " + "'" + password + "'";
        String st = "INSERT INTO users(name, username, password) VALUES(" + values + ")";

        return runUpdate(st);
    }

    public boolean createGroup(String name){
        String values = "'" + name + "'";
        String st = "INSERT INTO groups(name) VALUES("+values+")";
        return runUpdate(st);
    }

    public boolean joinGroup(int user_id, int group_id){
        String values = "'" + user_id +"'"+"," + "'" + group_id + "'";
        String st = "INSERT INTO user_group(user_id,group_id) VALUES("+values+")";
        return runUpdate(st);
    }

    public boolean sendMessage(String content,int user_id, int group_id){
        String values = "'" + content + "'" + "," +"'"+ user_id +"'"+"," + "'" + group_id + "'";
        String st = "INSERT INTO message(content,user_id,group_id) VALUES("+values+")";
        return runUpdate(st);
    }

    public boolean addToDo(String name, int group_id){
        String values = "'" + name + "'" + "," +"'"+ group_id +"'"+"," + "'" + 0 + "'";
        String st = "INSERT INTO task(name,group_id,done) VALUES("+values+")";
        return runUpdate(st);
    }

    public boolean checkToDo(int task_id){
        String values = "'" + task_id +"'";
        String st = "UPDATE task SET done = 1 WHERE task_id = "+ values;
        return runUpdate(st);
    }


    public boolean verifyLogin(String username, String password){
        String st = "SELECT password FROM users WHERE users.username = '" + username + "'";
        String retrieved_password = new String();
        ResultSet rs;

        try{
            rs = runSelect(st);
            if(rs.next())
                retrieved_password = rs.getString("password");
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        if(password.equals(retrieved_password))
            return true;
        else
            return false;
    }

    public User getUser(String username){
        String st = "SELECT users.name, users.username, users.user_id " +
                "FROM users WHERE users.username = '"+username+"'";
        ResultSet rs;
        String usern, name;
        int id;
        User u = null;

        try{
            rs = runSelect(st);
            name = rs.getString("name");
            usern = rs.getString("username");
            id = rs.getInt("user_id");
            u = new User(name, usern, id);
        }catch(SQLException e){
            e.printStackTrace(System.out);
            System.exit(1);
        }

        return u;
    }

    public ArrayList<User> getGroupUsers(int idUser){
        String st = "SELECT users.name, users.username, users.user_id " +
                    "FROM users " +
                    "WHERE users.user_id !="+idUser;
        ResultSet rs;
        String name;
        String username;
        int id;
        ArrayList<User> users= new ArrayList<>();

        try{
            rs = runSelect(st);
            while(rs.next()){
                name = rs.getString("name");
                username = rs.getString("username");
                id = rs.getInt("user_id");
                User u = new User(name, username, id);
                users.add(u);
            }
        }catch(SQLException e){
            e.printStackTrace(System.out);
            System.exit(1);
        }
        return users;
    }

    public ArrayList<Task> getGroupTasks(int group_id){
        String st = "SELECT task.task_id, task.name, task.done " +
                "FROM groups INNER JOIN task " +
                "ON (groups.group_id = task.group_id) " +
                "WHERE task.group_id = " + group_id +
                " ORDER BY task.done";

        ResultSet rs;
        String name;
        int id;
        boolean is_done;
        ArrayList<Task> tasks= new ArrayList<>();

        try{
            rs = runSelect(st);
            while(rs.next()){
                name = rs.getString("name");
                if(rs.getString("done").equals("1"))
                    is_done = true;
                else
                    is_done = false;
                id = rs.getInt("task_id");
                Task t = new Task(name, is_done, id);
                tasks.add(t);
            }
        }catch(SQLException e){
            e.printStackTrace(System.out);
            System.exit(1);
        }
        return tasks;
    }

    public ArrayList<Message> getGroupMessages(int group_id){
        String st =  "SELECT message_id, content, users.name " +
                    " FROM message INNER JOIN users ON (message.user_id = users.user_id) " +
                     "WHERE group_id ="+group_id;
        ResultSet rs;
        ArrayList<Message> messages= new ArrayList<>();

        try{
            rs = runSelect(st);
            while(rs.next()){
                int message_id = rs.getInt("message_id");
                String content = rs.getString("content");
                String name = rs.getString("name");
                Message m = new Message(content,new User(name,"",0),message_id);
                messages.add(m);

            }
        }catch(SQLException e){
            e.printStackTrace(System.out);
            System.exit(1);
        }
        return messages;
    }

}
