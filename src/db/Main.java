package db;

import logic.Group;
import logic.Message;
import logic.User;

import java.io.File;
import java.io.IOException;

/**
 * Created by dalugoga on 18-05-2017.
 */
public class Main {
    public static void main(String args[]) throws IOException{

        if(DBCreator.createDataBase())
            System.out.println("database is set");
        else {
            System.out.println("database is not set");
            System.exit(1);
        }

        DBConnection conn = new DBConnection();


        DBConnection dbc = new DBConnection();
        dbc.registerUser("Nuno Castro", "smurf", "rolodecarne");
        if(dbc.verifyLogin("smurf", "rolodecarne")){
            System.out.println("verified!");
        }else{
            System.out.println("not verified!");
        }

        //Group g = new Group("watever", 1);
        //System.out.println(g.toString());
    }
}
