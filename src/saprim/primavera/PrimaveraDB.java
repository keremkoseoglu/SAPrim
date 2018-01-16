/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package saprim.primavera;

/**
 * Class for direct database operations of Primavera
 * @author Kerem
 */

import com.microsoft.sqlserver.jdbc.*;
import saprim.*;
import java.sql.*;
import java.util.*;

public class PrimaveraDB {

    private Connection conn;    
    
    public PrimaveraDB() throws Exception
    {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }
    
    public void connect() throws Exception
    {
        String url = Main.config.primSqlUrl;
        conn = DriverManager.getConnection(url, Main.config.primSqlUser, Main.config.primSqlPass);
        if (conn == null)
        {
            throw new Exception("JDBC bağlantısı açılamadı, ayar dosyasını kontrol edin");
        }        
    }
    
    public void disconnect()
    {
        try
        {
            if (conn != null) conn.close();
        }
        catch(Exception ex) {}
    }
    
    public void executeQuery(String Query) throws Exception
    {
        Statement s = conn.createStatement();
        s.execute(Query);
    }        
    
    public void deleteSessions(String Username) throws Exception
    {
        executeQuery("delete from usession where user_id = (select user_id from users where user_name = '" + Username + "') ");
    }
    
}
