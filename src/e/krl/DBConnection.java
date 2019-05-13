package e.krl;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
    
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    protected static final String DB_URL = "jdbc:mysql://localhost/db_e_ticket";
    protected static final String USER = "root";
    protected static final String PASS = "";

    protected static Connection connection;
    protected static Statement statement;
    protected static ResultSet result;
    
    public static void RunConnection(){
         try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            
        } catch (ClassNotFoundException | SQLException error) {
             System.out.println("Koneksi Gagal" + error.getMessage() );
        }
         
    }
    
}
