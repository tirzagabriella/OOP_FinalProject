import javax.swing.*;
import java.text.SimpleDateFormat;

public class SchoolEntity {
    String name;
    String id;
    String role; // admin, teacher, student

    public SchoolEntity(){}

    public SchoolEntity(String id, String name, String role){
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public boolean CheckIn(){
        // get current timestamp to be stored when checking in
        String currentTimeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

        // query for inserting check-in data
        String query = "insert into attendance values('"+this.id+"','"+currentTimeStamp+"')";

        try{
            conn c1 = new conn();

            // run query
            c1.s.executeUpdate(query);

            return true;
        }catch(Exception ex){
            ex.printStackTrace();

            return false;
        }
    }
}