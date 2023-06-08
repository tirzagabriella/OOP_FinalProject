import java.sql.*;

public class conn
{
    Connection c;
    Statement s;
    public conn()
    {
        try
        {
            // db connect
            Class.forName("com.mysql.cj.jdbc.Driver");
            c=DriverManager.getConnection("jdbc:mysql://localhost/schooldb","admin","secret");
            s=c.createStatement();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
