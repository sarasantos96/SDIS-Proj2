package db;

import logic.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by dalugoga on 18-05-2017.
 */
public class DBConnection {
    private Connection conn;

    public DBConnection() {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost/sdis2", "root", "");
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
        String st = "INSERT INTO user(name, username, password) VALUES(" + values + ")";

        return runUpdate(st);
    }

    public boolean verifyLogin(String username, String password){
        String st = "SELECT password FROM user WHERE user.username = '" + username + "'";
        String retrieved_password = new String();
        ResultSet rs;

        try{
            rs = runSelect(st);
            rs.next();
            retrieved_password = rs.getString("password");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        if(password.equals(retrieved_password))
            return true;
        else
            return false;
    }

    public ArrayList<User> getGroupUsers(int group_id){
        String st = "SELECT user.name, user.username, user.user_id " +
                    "FROM user INNER JOIN user_group " +
                    "ON (user_group.user_id = user.user_id) " +
                    "WHERE group_id = " + group_id;
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

}
