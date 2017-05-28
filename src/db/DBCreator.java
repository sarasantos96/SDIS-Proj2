package db;

import java.io.*;

public class DBCreator {
    public static boolean createDataBase(){
        if(checkIfDataBaseExists())
            return true;

        try {
            String command = "bash ./src/db/create_db.sh";
            Process proc = Runtime.getRuntime().exec(command);
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean checkIfDataBaseExists(){
        File f = new File("./src/db/sdis2.db");
        if(f.exists() && !f.isDirectory()) {
            return true;
        }else{
            return false;
        }
    }
}
