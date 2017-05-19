package db;

/**
 * Created by dalugoga on 18-05-2017.
 */

import java.sql.*;
import java.io.*;

public class DBCreator {
    public static boolean createDataBase(){
        if(checkIfDataBaseExists("sdis2"))
            return true;

        try {
            String current = new java.io.File( "." ).getCanonicalPath();

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost", "root", "");
            ScriptRunner runner = new ScriptRunner(con, false, false);
            runner.runScript(new BufferedReader(new FileReader(current + "/src/db/sdis2.sql")));
            runner.runScript(new BufferedReader(new FileReader(current + "/src/db/populate.sql")));
        }catch(SQLException e){
            e.printStackTrace(System.out);
            System.exit(1);
        }catch(FileNotFoundException e){
            System.out.println("Database creation files not found");
            e.printStackTrace(System.out);
            System.exit(1);
        }catch(IOException e){
            e.printStackTrace(System.out);
            System.exit(1);
        }

        return true;
    }

    public static boolean checkIfDataBaseExists(String database_name){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost", "root", "");
            ResultSet resultSet = connection.getMetaData().getCatalogs();

            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);
                if (databaseName.equals(database_name)) {
                    resultSet.close();
                    return true;
                }
            }
            resultSet.close();
        }catch(SQLException e){
            e.printStackTrace(System.out);
            System.exit(1);
        }

        return false;
    }
}
